package vn.edu.hcmuaf.fit.fahabook.helper.data;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import vn.edu.hcmuaf.fit.fahabook.entity.Category;
import vn.edu.hcmuaf.fit.fahabook.entity.Product;
import vn.edu.hcmuaf.fit.fahabook.entity.enums.BookForm;
import vn.edu.hcmuaf.fit.fahabook.exception.AppException;
import vn.edu.hcmuaf.fit.fahabook.repository.CategoryRepository;
import vn.edu.hcmuaf.fit.fahabook.repository.ProductRepository;

@Slf4j
@Component
public class ImportDataHelper {
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final Executor asyncExecutor;

    public ImportDataHelper(CategoryRepository categoryRepository,
                            ProductRepository productRepository,
                            @Qualifier("asyncExecutor") Executor asyncExecutor) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
        this.asyncExecutor = asyncExecutor;
    }

    public CompletableFuture<List<Product>> importProducts(InputStream is) throws IOException {
        List<CompletableFuture<Product>> futures = new ArrayList<>();

        try (Workbook wb = new XSSFWorkbook(is)) {
            Sheet sheet = wb.getSheetAt(0);
            Iterator<Row> rows = sheet.iterator();
            if (!rows.hasNext()) return CompletableFuture.completedFuture(Collections.emptyList());

            Map<String, Integer> headers = buildHeaderMap(rows.next());
            while (rows.hasNext()) {
                Row row = rows.next();
                futures.add(CompletableFuture.supplyAsync(() -> processProductRow(row, headers), asyncExecutor));
            }
        }

        return CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]))
                .thenApply(v -> futures.stream()
                        .map(CompletableFuture::join)
                        .filter(Objects::nonNull)
                        .toList());
    }
    public CompletableFuture<List<Category>> importCategories(InputStream is) throws IOException {
        List<CompletableFuture<Category>> futures = new ArrayList<>();

        try (Workbook wb = new XSSFWorkbook(is)) {
            Sheet sheet = wb.getSheetAt(0);
            Iterator<Row> rows = sheet.iterator();
            if (!rows.hasNext()) {
                return CompletableFuture.completedFuture(Collections.emptyList());
            }

            Map<String, Integer> headers = buildHeaderMap(rows.next());
            while (rows.hasNext()) {
                Row row = rows.next();
                futures.add(
                        CompletableFuture.supplyAsync(() -> processCategoryRow(row, headers), asyncExecutor)
                );
            }
        }

        return CompletableFuture
                .allOf(futures.toArray(new CompletableFuture[0]))
                .thenApply(v -> futures.stream()
                        .map(CompletableFuture::join)
                        .filter(Objects::nonNull)
                        .toList());
    }

    private Category processCategoryRow(Row row, Map<String, Integer> headers) {
        try {
            String code = getString(row, headers.get("code"));
            if (code != null && categoryRepository.existsByCode(code)) {
                log.warn("Skip existed category at row {}: code={}", row.getRowNum(), code);
                return null;
            }
            return Category.builder()
                    .code(code)
                    .name(getString(row, headers.get("name")))
                    .description(getString(row, headers.get("description")))
                    .image(getString(row, headers.get("image")))
                    .build();
        } catch (Exception ex) {
            log.error("Error processing category row {}: {}", row.getRowNum(), ex.getMessage());
            return null;
        }
    }
    private Product processProductRow(Row row, Map<String, Integer> headers) {
        try {
            String categoryCode = getString(row, headers.get("category"));
            String code = getString(row, headers.get("code"));
            if (code != null && productRepository.existsByCode(code)) {
                log.info("Skip existed product at row {}: code={}", row.getRowNum(), code);
                return null;
            }

            Category category = categoryRepository.findByCode(categoryCode)
                    .orElseThrow(() -> new AppException("Không tìm thấy loại hàng với mã: " + categoryCode + " ở hàng " + row.getRowNum()));

            return Product.builder()
                    .category(category)
                    .code(Optional.ofNullable(code).orElse(UUID.randomUUID().toString()))
                    .name(getString(row, headers.get("name")))
                    .img(getString(row, headers.get("img")))
                    .detail(getString(row, headers.get("detail")))
                    .price(getDecimal(row, headers.get("price")))
                    .supplier(getString(row, headers.get("supplier")))
                    .publisher(getString(row, headers.get("publisher")))
                    .author(getString(row, headers.get("author")))
                    .publishYear(getInt(row, headers.get("publish_year")))
                    .weight(getInt(row, headers.get("weight")))
                    .size(getString(row, headers.get("size")))
                    .pageNumber(getInt(row, headers.get("page_number")))
                    .form(BookForm.valueOf(getString(row, headers.get("form"))))
                    .stock(0)
                    .discount(0.0)
                    .build();

        } catch (Exception ex) {
            log.error("Lỗi xử lý dòng {}: {}", row.getRowNum(), ex.getMessage());
            return null;
        }
    }



    private Map<String, Integer> buildHeaderMap(Row headerRow) {
        Map<String, Integer> map = new HashMap<>();
        for (Cell cell : headerRow) {
            String key = Optional.ofNullable(cell.getStringCellValue())
                    .map(String::trim)
                    .map(String::toLowerCase)
                    .orElse("");
            map.put(key, cell.getColumnIndex());
        }
        return map;
    }

    private static String getString(Row row, Integer idx) {
        if (idx == null) return null;
        Cell c = row.getCell(idx);
        return (c == null) ? null : c.toString().trim();
    }

    private static Double getNumber(Row row, Integer idx) {
        if (idx == null) return null;
        Cell c = row.getCell(idx);
        return (c != null && c.getCellType() == CellType.NUMERIC) ? c.getNumericCellValue() : null;
    }

    private static Integer getInt(Row row, Integer idx) {
        Double v = getNumber(row, idx);
        return (v == null) ? null : v.intValue();
    }

    private static BigDecimal getDecimal(Row row, Integer idx) {
        Double v = getNumber(row, idx);
        return (v == null) ? null : BigDecimal.valueOf(v);
    }
}

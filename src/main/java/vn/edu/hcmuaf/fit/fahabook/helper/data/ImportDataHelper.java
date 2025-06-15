package vn.edu.hcmuaf.fit.fahabook.helper.data;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.*;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import vn.edu.hcmuaf.fit.fahabook.dto.response.ApiResponse;
import vn.edu.hcmuaf.fit.fahabook.entity.Category;
import vn.edu.hcmuaf.fit.fahabook.entity.Product;
import vn.edu.hcmuaf.fit.fahabook.entity.enums.BookForm;
import vn.edu.hcmuaf.fit.fahabook.exception.AppException;
import vn.edu.hcmuaf.fit.fahabook.exception.ErrorCode;
import vn.edu.hcmuaf.fit.fahabook.repository.CategoryRepository;
import vn.edu.hcmuaf.fit.fahabook.repository.ProductRepository;

@Slf4j
@Component
public class ImportDataHelper {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    public ImportDataHelper(CategoryRepository categoryRepository, ProductRepository productRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }

    public List<Product> importProducts(InputStream is) throws IOException {
        List<Product> productsReaded = parseSheet(is, (row, headers) -> {
            String categoryCode = getString(row, headers.get("category"));
            String code = getString(row, headers.get("code"));
            Category category = categoryRepository
                    .findByCode(categoryCode)
                    .orElseThrow(() -> {
                        log.error("Category not found with code: {}", categoryCode);
                        return new AppException("Không tìm thầy loại hàng với mã: " + categoryCode +
                                " ở hàng  " + row.getRowNum());
                    });
            if (code != null && productRepository.existsByCode(code)) {
                log.info("Skip existed product at row {}: code={}", row.getRowNum(), code);
                return null;
            }


            return Product.builder()
                    .category(category)
                    .code(code)
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
                    .build();
        });
        return productsReaded.stream()
                .filter(Objects::nonNull)
                .toList();
    }

    public List<Category> importCategories(InputStream is) throws IOException {
        List<Category> categories = parseSheet(is, (row, headers) -> {
            String code = getString(row, headers.get("code"));
            if (code != null && categoryRepository.existsByCode(code)) {
                log.warn("Skip existed category at row {}: code={}", row.getRowNum(), code);
                return null;
            }
            return Category.builder()
                    .name(getString(row, headers.get("name")))
                    .description(getString(row, headers.get("description")))
                    .code(code)
                    .image(getString(row, headers.get("image")))
                    .build();
        });
        return categories.stream()
                .filter(Objects::nonNull)
                .toList();

    }


    private <T> List<T> parseSheet(InputStream is, BiFunction<Row, Map<String, Integer>, T> mapper) throws IOException {
        List<T> results = new ArrayList<>();
        try (Workbook wb = new XSSFWorkbook(is)) {
            Sheet sheet = wb.getSheetAt(0);
            Iterator<Row> rows = sheet.iterator();
            if (!rows.hasNext()) return results;

            Map<String, Integer> headers = buildHeaderMap(rows.next());
            rows.forEachRemaining(row -> results.add(mapper.apply(row, headers)));
        }
        return results;
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

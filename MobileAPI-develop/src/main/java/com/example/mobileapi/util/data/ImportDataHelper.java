package com.example.mobileapi.util.data;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.*;
import java.util.function.BiFunction;

import com.example.mobileapi.entity.Category;
import com.example.mobileapi.entity.Product;
import com.example.mobileapi.exception.AppException;
import com.example.mobileapi.exception.ErrorCode;
import com.example.mobileapi.repository.CategoryRepository;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ImportDataHelper {

    private final CategoryRepository categoryRepository;

    public ImportDataHelper(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Product> importProducts(InputStream is) throws IOException {
        return parseSheet(is, (row, headers) -> {
            String categoryCode = getString(row, headers.get("category"));
            Category category = categoryRepository.findByCode(categoryCode)
                    .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));
            return Product.builder()
                    .category(category)
                    .name(getString(row, headers.get("name")))
                    .img(getString(row, headers.get("img")))
                    .detail(getString(row, headers.get("detail")))
                    .price(getDecimal(row, headers.get("price")))
                    .supplier(getString(row, headers.get("supplier")))
                    .publisher(getString(row, headers.get("publisher")))
                    .author(getString(row, headers.get("author")))
                    .publishYear(getInt(row, headers.get("publishyear")))
                    .weight(getInt(row, headers.get("weight")))
                    .size(getString(row, headers.get("size")))
                    .pageNumber(getInt(row, headers.get("pagenumber")))
//                     .form(randomBookForm())
                    // .language(Language.VIETNAMESE)
                    .build();
        });
    }

    public List<Category> importCategories(InputStream is) throws IOException {
        return parseSheet(is, (row, headers) -> Category.builder()
                .name(getString(row, headers.get("name")))
                .description(getString(row, headers.get("description")))
                .code(getString(row, headers.get("code")))
                .build());
    }

    private <T> List<T> parseSheet(InputStream is,
            BiFunction<Row, Map<String, Integer>, T> mapper)
            throws IOException {
        List<T> results = new ArrayList<>();
        try (Workbook wb = new XSSFWorkbook(is)) {
            Sheet sheet = wb.getSheetAt(0);
            Iterator<Row> rows = sheet.iterator();
            if (!rows.hasNext())
                return results;

            Map<String, Integer> headers = buildHeaderMap(rows.next());
            rows.forEachRemaining(row -> {
                try {
                    results.add(mapper.apply(row, headers));
                } catch (Exception ex) {
                    log.error("❌ Lỗi parse row {}: {}", row.getRowNum(), ex.getMessage());
                }
            });
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
        if (idx == null)
            return null;
        Cell c = row.getCell(idx);
        return (c == null) ? null : c.toString().trim();
    }

    private static Double getNumber(Row row, Integer idx) {
        if (idx == null)
            return null;
        Cell c = row.getCell(idx);
        return (c != null && c.getCellType() == CellType.NUMERIC)
                ? c.getNumericCellValue()
                : null;
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

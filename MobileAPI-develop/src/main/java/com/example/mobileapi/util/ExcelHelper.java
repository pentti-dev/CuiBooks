package com.example.mobileapi.util;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.example.mobileapi.entity.Category;
import com.example.mobileapi.exception.AppException;
import com.example.mobileapi.exception.ErrorCode;
import com.example.mobileapi.repository.CategoryRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.example.mobileapi.entity.Product;
import com.example.mobileapi.entity.enums.BookForm;
import com.example.mobileapi.entity.enums.Language;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ExcelHelper {
    private final CategoryRepository injectedRepo;

    private static CategoryRepository categoryRepository;

    public ExcelHelper(CategoryRepository injectedRepo) {
        this.injectedRepo = injectedRepo;
    }

    @PostConstruct
    public void init() {
        categoryRepository = this.injectedRepo;
    }


    public static List<Product> parseExcel(InputStream inputStream) throws IOException {
        List<Product> products = new ArrayList<>();
        Workbook workbook = new XSSFWorkbook(inputStream);
        Sheet sheet = workbook.getSheetAt(0);
        Iterator<Row> rows = sheet.iterator();

        Map<String, Integer> headerMap = new HashMap<>();
        if (rows.hasNext()) {
            Row headerRow = rows.next();
            for (Cell cell : headerRow) {
                headerMap.put(cell.getStringCellValue().trim().toLowerCase(), cell.getColumnIndex());
            }
        }

        while (rows.hasNext()) {
            Row row = rows.next();
            try {
                Category category = categoryRepository.findByCode((getCellString(row, headerMap.get("category"))))
                        .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));

                Product product = Product.builder()
                        .category(category)
                        .name(getCellString(row, headerMap.get("name")))
                        .img(getCellString(row, headerMap.get("img")))
                        .detail(getCellString(row, headerMap.get("detail")))
                        .price(getCellBigDecimal(row, headerMap.get("price")))
                        .supplier(getCellString(row, headerMap.get("supplier")))
                        .publisher(getCellString(row, headerMap.get("publisher")))
                        .author(getCellString(row, headerMap.get("author")))
                        .publishYear(getCellInteger(row, headerMap.get("publishyear")))
                        .weight(getCellInteger(row, headerMap.get("weight")))
                        .size(getCellString(row, headerMap.get("size")))
                        .pageNumber(getCellInteger(row, headerMap.get("pagenumber")))
                        .form(getBookForm())
                        .language(Language.VIETNAMESE) // mặc định
                        .build();

                products.add(product);
            } catch (Exception e) {
                log.error("❌ Lỗi parse row số {}: {}", row.getRowNum(), e.getMessage());
            }
        }

        workbook.close();
        return products;
    }

    public static List<Category> importCategory(InputStream inputStream) throws IOException {
        List<Category> categories = new ArrayList<>();
        Workbook workbook = new XSSFWorkbook(inputStream);
        Sheet sheet = workbook.getSheetAt(0);
        Iterator<Row> rows = sheet.iterator();

        Map<String, Integer> headerMap = new HashMap<>();
        if (rows.hasNext()) {
            Row headerRow = rows.next();
            for (Cell cell : headerRow) {
                headerMap.put(cell.getStringCellValue().trim().toLowerCase(), cell.getColumnIndex());
            }
        }

        while (rows.hasNext()) {
            Row row = rows.next();
            try {
                Category category = Category.builder()
                        .name(getCellString(row, headerMap.get("name")))
                        .description(getCellString(row, headerMap.get("description")))
                        .code(getCellString(row, headerMap.get("code")))
                        .build();

                categories.add(category);
            } catch (Exception e) {
                log.error("❌ Lỗi parse row số {}: {}", row.getRowNum(), e.getMessage());
            }
        }

        workbook.close();
        return categories;
    }

    private static String getCellString(Row row, Integer index) {
        if (index == null) return null;
        Cell cell = row.getCell(index);
        return (cell != null) ? cell.toString().trim() : null;
    }

    private static Number getCellNumber(Row row, Integer index) {
        if (index == null) return null;
        Cell cell = row.getCell(index);
        return (cell != null) ? cell.getNumericCellValue() : null;
    }

    private static Integer getCellInteger(Row row, Integer index) {
        Number value = getCellNumber(row, index);
        return (value != null) ? value.intValue() : null;

    }

    private static BigDecimal getCellBigDecimal(Row row, Integer index) {
        Number value = getCellNumber(row, index);
        if (value == null) return null;
        return BigDecimal.valueOf(value.doubleValue());
    }


    private static BookForm getBookForm() {
        return EnumUtils.randomEnum(BookForm.class);
    }

}
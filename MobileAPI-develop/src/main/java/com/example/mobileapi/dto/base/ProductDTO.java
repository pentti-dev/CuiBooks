package com.example.mobileapi.dto.base;

import com.example.mobileapi.entity.enums.BookForm;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductDTO {
    UUID id;
    @NotBlank(message = "MISSING_PRODUCT_NAME")
    String name;
    @NotBlank(message = "MISSING_PRODUCT_IMG")
    String img;
    @NotNull(message = "MISSING_PRODUCT_PRICE")
    @Min(value = 1, message = "INVALID_PRODUCT_PRICE")
    BigDecimal price;

    String detail;

    String supplier;
    @NotBlank(message = "MISSING_PRODUCT_AUTHOR")
    String author;
    @Min(value = 0, message = "  INVALID_PRODUCT_PUBLIC_YEAR")
    Integer publishYear;

    String publisher;
    @Min(value = 0, message = "INVALID_PRODUCT_WEIGHT")
    Integer weight;

    String size;
    @Min(value = 0, message = "INVALID_PRODUCT_PAGE_NUMBER")
    Integer pageNumber;

    BookForm form;
    @Min(value = 0, message = "INVALID_PRODUCT_STOCK")
    Integer stock;

    String discount;

    UUID categoryId;

}

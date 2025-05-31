package com.example.mobileapi.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.UUID;

@Builder
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class ProductRequestDTO {
    @NotBlank(message = "MISSING_PRODUCT_NAME")
    String name;

    @NotBlank(message = "MISSING_PRODUCT_IMG")
    String img;

    @NotNull(message = "MISSING_PRODUCT_PRICE")
    @DecimalMin(value = "0.0", inclusive = false, message = "MISSING_PRODUCT_PRICE")
    BigDecimal price;

    @NotBlank(message = "MISSING_PRODUCT_DETAIL")
    String detail;

    @NotBlank(message = "MISSING_PRODUCT_SUPPLIER")
    String supplier;

    @NotBlank(message = "MISSING_PRODUCT_AUTHOR")
    String author;

    @NotNull(message = "MISSING_PRODUCT_PUBLISH_YEAR")
    @Min(value = 1, message = "MISSING_PRODUCT_PUBLISH_YEAR")
    Integer publishYear;

    @NotBlank(message = "MISSING_PRODUCT_PUBLISHER")
    String publisher;

    @NotNull(message = "MISSING_PRODUCT_WEIGHT")
    @Min(value = 1, message = "MISSING_PRODUCT_WEIGHT")
    Integer weight;

    @NotBlank(message = "MISSING_PRODUCT_SIZE")
    String size;

    @NotNull(message = "MISSING_PRODUCT_PAGE_NUMBER")
    @Min(value = 1, message = "MISSING_PRODUCT_PAGE_NUMBER")
    Integer pageNumber;

    @NotBlank(message = "MISSING_PRODUCT_FORM")
    String form;

    @NotNull(message = "MISSING_CATEGORY_ID")
    UUID categoryId;
}

package com.example.mobileapi.dto.request;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class ProductRequestDTO {
    String name;
    String img;
    BigDecimal price;
    String detail;
    String supplier;
    String author;
    Integer publishYear;
    String publisher;
    String language;
    Integer weight;
    String size;
    Integer pageNumber;
    String form;
    UUID categoryId;
}

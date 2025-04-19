package com.example.mobileapi.dto.request;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class ProductRequestDTO {
    String name;
    String img;
    int price;
    String detail;
    String supplier;
    String author;
    Integer publishYear;
    String publisher;
    String language;
    Byte weight;
    String size;
    Integer pageNumber;
    String form;
    Integer categoryId;
}

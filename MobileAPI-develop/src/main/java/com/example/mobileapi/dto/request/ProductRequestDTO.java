package com.example.mobileapi.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class ProductRequestDTO {
    String name;
    String img;
    int price;
    String detail;
    String categoryName;

}

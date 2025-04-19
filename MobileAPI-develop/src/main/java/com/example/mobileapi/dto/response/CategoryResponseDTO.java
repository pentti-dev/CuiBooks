package com.example.mobileapi.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Builder
@Getter
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class CategoryResponseDTO {
    Integer id;
    String name;
    String img;
}

package com.example.mobileapi.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class CategoryRequestDTO {
    @NotBlank(message = "Tên danh mục không được để trống")
    String name;
    @NotBlank(message = "Mô tả không được để trống")
    String img;
}

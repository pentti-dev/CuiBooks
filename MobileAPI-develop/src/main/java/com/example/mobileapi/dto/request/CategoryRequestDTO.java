package com.example.mobileapi.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Getter
@Setter
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class CategoryRequestDTO {
    UUID id;
    @NotBlank(message = "MISSING_CATEGORY_NAME")
    String name;
    @NotBlank(message = "MISSING_CATEGORY_CODE")
    String code;
    String description;
}

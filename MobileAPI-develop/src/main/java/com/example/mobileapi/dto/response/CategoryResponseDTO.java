package com.example.mobileapi.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Builder
@Getter
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class CategoryResponseDTO {
    UUID id;
    String name;
    String img;
}

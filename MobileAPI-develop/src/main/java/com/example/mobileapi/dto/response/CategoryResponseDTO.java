package com.example.mobileapi.dto.response;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CategoryResponseDTO {
    private Integer id;
    private String name;
    private String img;
}

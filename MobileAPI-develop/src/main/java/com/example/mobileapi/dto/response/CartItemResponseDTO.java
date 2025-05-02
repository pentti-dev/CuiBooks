package com.example.mobileapi.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class CartItemResponseDTO {
    private UUID id;
    private ProductResponseDTO product;
    private Integer quantity;
}

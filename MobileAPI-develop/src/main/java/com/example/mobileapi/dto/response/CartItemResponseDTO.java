package com.example.mobileapi.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CartItemResponseDTO {
    private int id;
    private ProductResponseDTO product;
    private int quantity;
}

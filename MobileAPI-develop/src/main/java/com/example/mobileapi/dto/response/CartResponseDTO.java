package com.example.mobileapi.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;
@Builder
@Getter
public class CartResponseDTO {
    private int id;
    private int customerId;
    private float totalPrice;
    private List<CartItemResponseDTO> cartItems;
}

package com.example.mobileapi.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartItemRequestDTO {
    private int cartId;
    private int productId;
    private int quantity;
}

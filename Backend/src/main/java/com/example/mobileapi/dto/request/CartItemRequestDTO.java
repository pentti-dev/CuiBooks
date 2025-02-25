package com.example.mobileapi.dto.request;

import com.example.mobileapi.model.Cart;
import com.example.mobileapi.model.Product;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartItemRequestDTO {
    private int cartId;
    private int productId;
    private int quantity;
}

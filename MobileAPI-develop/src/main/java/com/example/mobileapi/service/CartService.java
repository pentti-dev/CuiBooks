package com.example.mobileapi.service;

import com.example.mobileapi.dto.request.CartRequestDTO;
import com.example.mobileapi.dto.response.CartResponseDTO;

public interface CartService {
    int saveCart(CartRequestDTO cartRequestDTO);

    CartResponseDTO getCart(int cartId);

    CartResponseDTO getCartByCustomerId(int id);

    CartResponseDTO getCartByUsername(String username);

    Integer getQuantityCartItemInCart(int cartId);
}

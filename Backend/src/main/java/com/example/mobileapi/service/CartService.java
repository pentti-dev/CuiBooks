package com.example.mobileapi.service;

import com.example.mobileapi.dto.request.CartRequestDTO;
import com.example.mobileapi.dto.response.CartResponseDTO;
import com.example.mobileapi.model.Cart;

public interface CartService {
    int saveCart(CartRequestDTO cartRequestDTO);

    CartResponseDTO getCart(int cartId);

    CartResponseDTO getCartByCustomerId(int id);

    int getQuantityCartItemInCart(int cartId);
}

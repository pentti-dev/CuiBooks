package com.example.mobileapi.service;

import com.example.mobileapi.dto.request.CartRequestDTO;
import com.example.mobileapi.dto.response.CartResponseDTO;
import com.example.mobileapi.exception.AppException;

public interface CartService {
    Integer saveCart(CartRequestDTO cartRequestDTO);

    CartResponseDTO getCartById(int cartId) throws AppException;

    CartResponseDTO getCartByCustomerId(int id) throws AppException;

    CartResponseDTO getCartByUsername(String username);

    Integer getQuantityCartItemInCart(int cartId);
}

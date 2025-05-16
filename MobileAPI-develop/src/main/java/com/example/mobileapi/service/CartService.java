package com.example.mobileapi.service;

import com.example.mobileapi.dto.request.CartRequestDTO;
import com.example.mobileapi.dto.response.CartResponseDTO;
import com.example.mobileapi.exception.AppException;

import java.util.UUID;

public interface CartService {
    UUID saveCart(CartRequestDTO cartRequestDTO) throws AppException;

    CartResponseDTO getCartById(UUID cartId) throws AppException;

    CartResponseDTO getCartByCustomerId(UUID id) throws AppException;

    CartResponseDTO getCartByUsername(String username);

    Integer getQuantityCartItemInCart(UUID cartId);
}

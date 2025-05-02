package com.example.mobileapi.service;

import com.example.mobileapi.dto.request.CartItemRequestDTO;
import com.example.mobileapi.dto.response.CartItemResponseDTO;
import com.example.mobileapi.exception.AppException;

import java.util.UUID;

public interface CartItemService {
    UUID saveCartItem(CartItemRequestDTO cartItem) throws AppException;

    CartItemResponseDTO getCartItem(UUID  cartItemId) throws AppException;

    void deleteCartItem(UUID  cartItemId);

    void updateCartItem(UUID  id, CartItemRequestDTO cartItem);

    void updateCartItemQuantity(UUID  cartItemId, int quantity);

    void deleteCartItemByCartId(UUID  cartId);

}

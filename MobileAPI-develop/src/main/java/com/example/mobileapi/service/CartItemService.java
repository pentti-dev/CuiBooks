package com.example.mobileapi.service;

import com.example.mobileapi.dto.request.CartItemRequestDTO;
import com.example.mobileapi.dto.response.CartItemResponseDTO;

public interface CartItemService {
    Integer saveCartItem(CartItemRequestDTO cartItem);

    CartItemResponseDTO getCartItem(int cartItemId);

    void deleteCartItem(int cartItemId);

    void updateCartItem(int id, CartItemRequestDTO cartItem);

    void updateCartItemQuantity(int cartItemId, int quantity);

    void deleteCartItemByCartId(int cartId);

}

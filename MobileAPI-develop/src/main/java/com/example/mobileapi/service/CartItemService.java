package com.example.mobileapi.service;

import com.example.mobileapi.dto.request.CartItemRequestDTO;
import com.example.mobileapi.dto.response.CartItemResponseDTO;
import com.example.mobileapi.model.CartItem;

public interface CartItemService {
    int saveCartItem(CartItemRequestDTO cartItem);

    CartItemResponseDTO getCartItem(int cartItemId);

    void deleteCartItem(int cartItemId);

    void updateCartItem(int id, CartItemRequestDTO cartItem);

    void updateCartItemQuantity(int cartItemId, int quantity);

    void deleteCartItemByCartId(int cartId);

}

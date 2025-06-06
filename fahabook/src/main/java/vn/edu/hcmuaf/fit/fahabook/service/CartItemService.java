package vn.edu.hcmuaf.fit.fahabook.service;

import java.util.UUID;

import vn.edu.hcmuaf.fit.fahabook.dto.request.CartItemRequestDTO;
import vn.edu.hcmuaf.fit.fahabook.dto.response.CartItemResponseDTO;
import vn.edu.hcmuaf.fit.fahabook.exception.AppException;

public interface CartItemService {
    UUID saveCartItem(CartItemRequestDTO cartItem) throws AppException;

    CartItemResponseDTO getCartItem(UUID cartItemId) throws AppException;

    void deleteCartItem(UUID cartItemId);

    void updateCartItem(UUID id, CartItemRequestDTO cartItem);

    void updateCartItemQuantity(UUID cartItemId, int quantity);

    void deleteCartItemByCartId(UUID cartId);
}

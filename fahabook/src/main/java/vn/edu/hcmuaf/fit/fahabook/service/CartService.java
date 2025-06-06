package vn.edu.hcmuaf.fit.fahabook.service;

import java.util.UUID;

import vn.edu.hcmuaf.fit.fahabook.dto.request.CartRequestDTO;
import vn.edu.hcmuaf.fit.fahabook.dto.response.CartResponseDTO;
import vn.edu.hcmuaf.fit.fahabook.exception.AppException;

public interface CartService {
    UUID saveCart(CartRequestDTO cartRequestDTO) throws AppException;

    CartResponseDTO getCartById(UUID cartId) throws AppException;

    CartResponseDTO getCartByCustomerId(UUID id) throws AppException;

    CartResponseDTO getCartByUsername(String username);

    Integer getQuantityCartItemInCart(UUID cartId);
}

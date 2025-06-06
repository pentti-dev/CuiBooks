package com.example.mobileapi.service.impl;

import com.example.mobileapi.dto.request.CartItemRequestDTO;
import com.example.mobileapi.dto.response.CartItemResponseDTO;
import com.example.mobileapi.entity.CartItem;
import com.example.mobileapi.entity.enums.StockAction;
import com.example.mobileapi.exception.AppException;
import com.example.mobileapi.repository.CartItemRepository;
import com.example.mobileapi.service.CartItemService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = lombok.AccessLevel.PRIVATE)
public class CartItemServiceImpl implements CartItemService {
    CartItemRepository cartItemRepository;
    CartServiceImpl cartServiceImpl;
    ProductServiceImpl productServiceImpl;

    @Override
    public UUID saveCartItem(CartItemRequestDTO cartItem) throws AppException {
        UUID cartId = cartItem.getCartId();
        UUID productId = cartItem.getProductId();
        productServiceImpl.checkQuantityAvailability(productId, cartItem.getQuantity(), StockAction.CHECK);

        // Tìm CartItem dựa trên cartId và productId
        CartItem existingCartItem = cartItemRepository.findByCartIdAndProductId(cartId, productId);

        if (existingCartItem != null) {
            // Nếu sản phẩm đã tồn tại trong giỏ hàng, cập nhật số lượng sản phẩm
            existingCartItem.setQuantity(existingCartItem.getQuantity() + cartItem.getQuantity());
            cartItemRepository.save(existingCartItem);
            return existingCartItem.getId();
        } else {
            // Nếu sản phẩm chưa tồn tại trong giỏ hàng, tạo mới CartItem
            CartItem newCartItem = CartItem.builder()
                    .cart(cartServiceImpl.getByCartId(cartId))
                    .quantity(cartItem.getQuantity())
                    .product(productServiceImpl.getById(productId))
                    .build();
            return cartItemRepository.save(newCartItem).getId();
        }
    }

    @Override
    public CartItemResponseDTO getCartItem(UUID cartItemId) throws AppException {
        CartItem cartItem = getByCartId(cartItemId);
        return CartItemResponseDTO.builder()
                .id(cartItem.getId())
                .product(productServiceImpl.getProductById(cartItem.getProduct().getId()))
                .quantity(cartItem.getQuantity())
                .build();
    }

    @Override
    public void deleteCartItem(UUID cartItemId) {
        cartItemRepository.deleteById(cartItemId);
    }

    @Override
    public void updateCartItem(UUID id, CartItemRequestDTO cartItem) {
        CartItem cartI = getByCartId(id);
        cartI.setQuantity(cartItem.getQuantity());
        cartItemRepository.save(cartI);
    }

    @Override
    public void updateCartItemQuantity(UUID cartItemId, int quantity) {
        CartItem cartI = getByCartId(cartItemId);
        cartI.setQuantity(cartI.getQuantity() + quantity);
        if (cartI.getQuantity() <= 0) {
            cartItemRepository.delete(cartI);
        } else {
            cartItemRepository.save(cartI);
        }

    }

    @Override
    public void deleteCartItemByCartId(UUID cartId) {
        cartItemRepository.deleteByCartId(cartId);
    }

    public CartItem getByCartId(UUID cartItemId) {
        return cartItemRepository.findById(cartItemId).orElse(null);
    }

    public List<CartItemResponseDTO> getCartItemsByCartId(UUID cartId) {
        List<CartItem> cartItems = cartItemRepository.findByCartId(cartId);
        return cartItems.stream().map(cartItem -> CartItemResponseDTO.builder()
                .id(cartItem.getId())
                .product(productServiceImpl.getProductById(cartItem.getProduct().getId()))
                .quantity(cartItem.getQuantity())
                .build()).toList();
    }
}

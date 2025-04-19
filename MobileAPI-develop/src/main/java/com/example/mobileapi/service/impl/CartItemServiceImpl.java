package com.example.mobileapi.service.impl;

import com.example.mobileapi.dto.request.CartItemRequestDTO;
import com.example.mobileapi.dto.response.CartItemResponseDTO;
import com.example.mobileapi.entity.CartItem;
import com.example.mobileapi.exception.AppException;
import com.example.mobileapi.repository.CartItemRepository;
import com.example.mobileapi.service.CartItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class CartItemServiceImpl implements CartItemService {
    private final CartItemRepository cartItemRepository;
    private final CartServiceImpl cartServiceImpl;
    private final ProductServiceImpl productServiceImpl;

    @Override
    public Integer saveCartItem(CartItemRequestDTO cartItem) {
        int cartId = cartItem.getCartId();
        int productId = cartItem.getProductId();

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
    public CartItemResponseDTO getCartItem(int cartItemId) throws AppException {
        CartItem cartItem = getByCartId(cartItemId);
        return CartItemResponseDTO.builder()
                .id(cartItem.getId())
                .product(productServiceImpl.getProductById(cartItem.getProduct().getId()))
                .quantity(cartItem.getQuantity())
                .build();
    }

    @Override
    public void deleteCartItem(int cartItemId) {
        cartItemRepository.deleteById(cartItemId);
    }

    @Override
    public void updateCartItem(int id, CartItemRequestDTO cartItem) {
        CartItem cartI = getByCartId(id);
        cartI.setQuantity(cartItem.getQuantity());
        cartItemRepository.save(cartI);
    }

    @Override
    public void updateCartItemQuantity(int cartItemId, int quantity) {
        CartItem cartI = getByCartId(cartItemId);
        cartI.setQuantity(cartI.getQuantity() + quantity);
        if (cartI.getQuantity() <= 0) {
            cartItemRepository.delete(cartI);
        } else {
            cartItemRepository.save(cartI);
        }

    }

    @Override
    public void deleteCartItemByCartId(int cartId) {
        cartItemRepository.deleteByCartId(cartId);
    }

    public CartItem getByCartId(int cartItemId) {
        return cartItemRepository.findById(cartItemId).orElse(null);
    }

    public List<CartItemResponseDTO> getCartItemsByCartId(int cartId) {
        List<CartItem> cartItems = cartItemRepository.findByCartId(cartId);
        return cartItems.stream().map(cartItem -> {
            try {
                return CartItemResponseDTO.builder()
                        .id(cartItem.getId())
                        .product(productServiceImpl.getProductById(cartItem.getProduct().getId()))
                        .quantity(cartItem.getQuantity())
                        .build();
            } catch (AppException e) {
                throw new RuntimeException(e);
            }
        }).collect(Collectors.toList());
    }
}

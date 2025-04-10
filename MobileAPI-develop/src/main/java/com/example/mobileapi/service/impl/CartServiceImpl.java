package com.example.mobileapi.service.impl;

import com.example.mobileapi.dto.request.CartRequestDTO;
import com.example.mobileapi.dto.response.CartItemResponseDTO;
import com.example.mobileapi.dto.response.CartResponseDTO;
import com.example.mobileapi.exception.AppException;
import com.example.mobileapi.exception.ErrorCode;
import com.example.mobileapi.model.Cart;
import com.example.mobileapi.model.CartItem;
import com.example.mobileapi.repository.CartRepository;
import com.example.mobileapi.service.CartService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = lombok.AccessLevel.PRIVATE)
public class CartServiceImpl implements CartService {
    CartRepository cartRepository;
    CustomerServiceImpl customerServiceImpl;
    ProductServiceImpl productServiceImpl;

    @Override
    public Integer saveCart(CartRequestDTO cartRequestDTO) {
        Cart cart = Cart.builder()
                .customer(customerServiceImpl.getCustomerById(cartRequestDTO.getCustomerId()))
                .build();
        return cartRepository.save(cart).getId();
    }

    @Override
    public CartResponseDTO getCart(int cartId) throws AppException {
        Cart cart = getByCartId(cartId);
        if (cart == null) {
            throw new AppException(ErrorCode.INVALID_CART);
        }

        List<CartItemResponseDTO> cartItemResponseDTOs = cart.getCartItems().stream()
                .map(this::convertToCartItemResponseDTO)
                .toList();

        return CartResponseDTO.builder()
                .id(cart.getId())
                .customerId(cart.getCustomer().getId())
                .cartItems(cartItemResponseDTOs)
                .build();
    }

    public Cart getByCartId(int cartId) {
        return cartRepository.findById(cartId).orElse(null);
    }

    private CartItemResponseDTO convertToCartItemResponseDTO(CartItem cartItem) {
        return CartItemResponseDTO.builder()
                .id(cartItem.getId())
                .product(productServiceImpl.getProductById(cartItem.getProduct().getId()))
                .quantity(cartItem.getQuantity())
                .build();
    }

    @Override
    public CartResponseDTO getCartByCustomerId(int customerId) {
        Cart cart = getByCustomerId(customerId);
        if (cart == null) {
            return null; // Or throw an exception if preferred
        }

        List<CartItemResponseDTO> cartItemResponseDTOs = cart.getCartItems().stream()
                .map(this::convertToCartItemResponseDTO)
                .toList();

        return CartResponseDTO.builder()
                .id(cart.getId())
                .customerId(cart.getCustomer().getId())
                .cartItems(cartItemResponseDTOs)
                .build();
    }

    /**
     * @param username username khach hang
     * @return gio hang cua khach hang
     */
    @Override
    public CartResponseDTO getCartByUsername(String username) {
        Cart cart = getByUsername(username);
        if (cart == null) {
            return null; // Or throw an exception if preferred
        }

        List<CartItemResponseDTO> cartItemResponseDTOs = cart.getCartItems().stream()
                .map(this::convertToCartItemResponseDTO)
                .toList();

        return CartResponseDTO.builder()
                .id(cart.getId())
                .customerId(cart.getCustomer().getId())
                .cartItems(cartItemResponseDTOs)
                .build();
    }

    public Cart getByCustomerId(int customerId) {
        return cartRepository.findByCustomerId(customerId).orElse(null);
    }

    public Cart getByUsername(String username) {
        return cartRepository.findCartByCustomer_Username(username).orElse(null);
    }

    public Integer getQuantityCartItemInCart(int cartId) {
        Cart cart = getByCartId(cartId);
        if (cart == null) {
            return 0;
        }

        return cart.getCartItems().stream()
                .mapToInt(CartItem::getQuantity)
                .sum();
    }

}

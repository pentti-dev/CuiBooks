package com.example.mobileapi.service.impl;

import com.example.mobileapi.dto.request.CartRequestDTO;
import com.example.mobileapi.dto.response.CartResponseDTO;
import com.example.mobileapi.exception.AppException;
import com.example.mobileapi.exception.ErrorCode;
import com.example.mobileapi.entity.Cart;
import com.example.mobileapi.entity.CartItem;
import com.example.mobileapi.mapper.CartItemMapper;
import com.example.mobileapi.repository.CartRepository;
import com.example.mobileapi.service.CartService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = lombok.AccessLevel.PRIVATE)
public class CartServiceImpl implements CartService {
    CartRepository cartRepository;
    CustomerServiceImpl customerServiceImpl;
    CartItemMapper cartItemMapper;

    @Override
    public UUID saveCart(CartRequestDTO cartRequestDTO) {
        Cart cart = Cart.builder()
                .customer(customerServiceImpl.getCustomerById(cartRequestDTO.getCustomerId()))
                .build();
        return cartRepository.save(cart).getId();
    }

    @Override
    public CartResponseDTO getCartById(UUID cartId) throws AppException {
        Cart cart = getByCartId(cartId);
        if (cart == null) {
            throw new AppException(ErrorCode.INVALID_CART);
        }
        List<CartItem> cartItems = cart.getCartItems();
        return CartResponseDTO.builder()
                .id(cartId)
                .customerId(cart.getCustomer().getId())
                .cartItems(cartItemMapper.toCartItemResponseDTOList(cartItems))
                .build();
    }

    public Cart getByCartId(UUID cartId) {
        return cartRepository.findById(cartId).orElse(null);
    }


    @Override
    public CartResponseDTO getCartByCustomerId(UUID customerId) throws AppException {
        Cart cart = getByCustomerId(customerId);
        if (cart == null) {
            throw new AppException(ErrorCode.INVALID_CART);
        }

        List<CartItem> cartItems = cart.getCartItems();
        return CartResponseDTO.builder()
                .id(cart.getId())
                .customerId(cart.getCustomer().getId())
                .cartItems(cartItemMapper.toCartItemResponseDTOList(cartItems))
                .build();
    }

    /**
     * @param username username khach hang
     * @return gio hang cua khach hang
     */
    @SneakyThrows
    @Override
    public CartResponseDTO getCartByUsername(String username) {
        Cart cart = getByUsername(username);
        if (cart == null) {
            throw new AppException(ErrorCode.INVALID_CART);
        }
        List<CartItem> cartItems = cart.getCartItems();
        return CartResponseDTO.builder()
                .id(cart.getId())
                .customerId(cart.getCustomer().getId())
                .cartItems(cartItemMapper.toCartItemResponseDTOList(cartItems))
                .build();
    }

    public Cart getByCustomerId(UUID customerId) {
        return cartRepository.findByCustomerId(customerId).orElse(null);
    }

    public Cart getByUsername(String username) {
        return cartRepository.findCartByCustomer_Username(username).orElse(null);
    }

    public Integer getQuantityCartItemInCart(UUID cartId) {
        Cart cart = getByCartId(cartId);
        if (cart == null) {
            return 0;
        }

        return cart.getCartItems().stream()
                .mapToInt(CartItem::getQuantity)
                .sum();
    }

}

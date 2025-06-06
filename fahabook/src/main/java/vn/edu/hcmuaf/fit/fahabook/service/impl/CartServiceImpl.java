package vn.edu.hcmuaf.fit.fahabook.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import vn.edu.hcmuaf.fit.fahabook.dto.request.CartRequestDTO;
import vn.edu.hcmuaf.fit.fahabook.dto.response.CartResponseDTO;
import vn.edu.hcmuaf.fit.fahabook.entity.Cart;
import vn.edu.hcmuaf.fit.fahabook.entity.CartItem;
import vn.edu.hcmuaf.fit.fahabook.exception.AppException;
import vn.edu.hcmuaf.fit.fahabook.exception.ErrorCode;
import vn.edu.hcmuaf.fit.fahabook.mapper.CartItemMapper;
import vn.edu.hcmuaf.fit.fahabook.repository.CartRepository;
import vn.edu.hcmuaf.fit.fahabook.service.CartService;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = lombok.AccessLevel.PRIVATE)
public class CartServiceImpl implements CartService {
    CartRepository cartRepository;
    CustomerServiceImpl customerServiceImpl;
    CartItemMapper cartItemMapper;

    @Override
    @Transactional
    public UUID saveCart(CartRequestDTO cartRequestDTO) throws AppException {
        Cart cart = Cart.builder()
                .customer(customerServiceImpl.getCustomerById(cartRequestDTO.getCustomerId()))
                .build();
        log.info("Save cart : {}", cart.getCustomer().getFullname());
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

        return cart.getCartItems().stream().mapToInt(CartItem::getQuantity).sum();
    }
}

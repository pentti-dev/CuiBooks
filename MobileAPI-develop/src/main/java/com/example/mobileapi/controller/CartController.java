package com.example.mobileapi.controller;

import com.example.mobileapi.dto.request.CartRequestDTO;
import com.example.mobileapi.dto.response.CartResponseDTO;
import com.example.mobileapi.service.CartService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
@Tag(name = "Cart", description = "Cart API")
public class CartController {
    private final CartService cartService;

    @PostMapping
    public int saveCart(@RequestBody CartRequestDTO cartRequestDTO) {
        return cartService.saveCart(cartRequestDTO);
    }

    @GetMapping("/{customerId}")
    public CartResponseDTO getCartByCustomerId(@PathVariable int customerId) {
        return cartService.getCartByCustomerId(customerId);
    }

    @GetMapping("/quantity/{cartId}")
    public int getCart(@PathVariable int cartId) {
        return cartService.getQuantityCartItemInCart(cartId);
    }

}

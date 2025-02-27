package com.example.mobileapi.controller;

import com.example.mobileapi.dto.request.CartItemRequestDTO;
import com.example.mobileapi.service.CartItemService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cartItem")
@RequiredArgsConstructor
@Tag(name = "CartItem", description = "CartItem API")
public class CartItemController {
    private final CartItemService cartItemService;

    @PostMapping
    public int saveCartItem(@RequestBody CartItemRequestDTO cartItemRequestDTO) {
        return cartItemService.saveCartItem(cartItemRequestDTO);
    }

    @PutMapping("/updatequantity/{cartItemId}")
    public void updateCartItem(@PathVariable("cartItemId") int cartItemId, @RequestParam int quantity) {
        cartItemService.updateCartItemQuantity(cartItemId, quantity);
    }

    @DeleteMapping("/{cartItemId}")
    public void deleteCartItem(@PathVariable("cartItemId") int cartItemId) {
        cartItemService.deleteCartItem(cartItemId);
    }

    @DeleteMapping("/cartId/{cartId}")
    public void deleteCartItemByCartId(@PathVariable("cartId") int cartId) {
        cartItemService.deleteCartItemByCartId(cartId);
    }
}

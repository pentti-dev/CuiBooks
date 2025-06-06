package vn.edu.hcmuaf.fit.fahabook.controller;

import java.util.UUID;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import vn.edu.hcmuaf.fit.fahabook.dto.request.CartItemRequestDTO;
import vn.edu.hcmuaf.fit.fahabook.dto.response.ApiResponse;
import vn.edu.hcmuaf.fit.fahabook.service.CartItemService;

@RestController
@RequestMapping("/api/cartItem")
@RequiredArgsConstructor
@Tag(name = "CartItem", description = "CartItem API")
public class CartItemController {
    private final CartItemService cartItemService;

    @PostMapping
    public ApiResponse<Void> saveCartItem(@RequestBody @Valid CartItemRequestDTO cartItemRequestDTO) {
        cartItemService.saveCartItem(cartItemRequestDTO);
        return ApiResponse.success();
    }

    @PutMapping("/updatequantity/{cartItemId}")
    public ApiResponse<Void> updateCartItem(@PathVariable("cartItemId") UUID cartItemId, @RequestParam int quantity) {
        cartItemService.updateCartItemQuantity(cartItemId, quantity);
        return ApiResponse.success();
    }

    @PutMapping("/update/{cartItemId}")
    public ApiResponse<Void> updateQuantityCartItem(
            @PathVariable("cartItemId") UUID cartItemId, @RequestBody CartItemRequestDTO cartItem) {
        cartItemService.updateCartItem(cartItemId, cartItem);
        return ApiResponse.success();
    }

    @DeleteMapping("/{cartItemId}")
    public ApiResponse<Void> deleteCartItem(@PathVariable("cartItemId") UUID cartItemId) {
        cartItemService.deleteCartItem(cartItemId);

        return ApiResponse.success();
    }

    @DeleteMapping("/cartId/{cartId}")
    public ApiResponse<Void> deleteCartItemByCartId(@PathVariable("cartId") UUID cartId) {
        cartItemService.deleteCartItemByCartId(cartId);
        return ApiResponse.success();
    }
}

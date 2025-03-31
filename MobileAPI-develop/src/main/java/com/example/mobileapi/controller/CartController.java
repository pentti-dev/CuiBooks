package com.example.mobileapi.controller;

import com.example.mobileapi.dto.request.CartRequestDTO;
import com.example.mobileapi.dto.response.ApiResponse;
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
    public ApiResponse<Void> saveCart(@RequestBody CartRequestDTO cartRequestDTO) {
        cartService.saveCart(cartRequestDTO);
        return ApiResponse.success("Thêm giỏ hàng thành công");
    }

    @GetMapping("/{customerId}")
    public ApiResponse<CartResponseDTO> getCartByCustomerId(@PathVariable int customerId) {
        return ApiResponse.<CartResponseDTO>builder()
                .data(cartService.getCartByCustomerId(customerId))
                .build();
    }

    @GetMapping("/quantity/{cartId}")
    public ApiResponse<Integer> getCart(@PathVariable int cartId) {

        return ApiResponse.<Integer>builder()
                .data(cartService.getQuantityCartItemInCart(cartId))
                .build();
    }

}

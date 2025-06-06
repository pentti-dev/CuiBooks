package vn.edu.hcmuaf.fit.fahabook.controller;

import java.util.UUID;

import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import vn.edu.hcmuaf.fit.fahabook.dto.request.CartRequestDTO;
import vn.edu.hcmuaf.fit.fahabook.dto.response.ApiResponse;
import vn.edu.hcmuaf.fit.fahabook.dto.response.CartResponseDTO;
import vn.edu.hcmuaf.fit.fahabook.exception.AppException;
import vn.edu.hcmuaf.fit.fahabook.service.CartService;

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
    public ApiResponse<CartResponseDTO> getCartByCustomerId(@PathVariable UUID customerId) throws AppException {
        return ApiResponse.<CartResponseDTO>builder()
                .data(cartService.getCartByCustomerId(customerId))
                .build();
    }

    @GetMapping("/quantity/{cartId}")
    public ApiResponse<Integer> getCart(@PathVariable UUID cartId) {

        return ApiResponse.<Integer>builder()
                .data(cartService.getQuantityCartItemInCart(cartId))
                .build();
    }
}

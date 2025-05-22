package com.example.mobileapi.controller;

import com.example.mobileapi.dto.request.OrderRequestDTO;
import com.example.mobileapi.dto.response.ApiResponse;
import com.example.mobileapi.dto.response.OrderResponseDTO;
import com.example.mobileapi.entity.enums.OrderMethod;
import com.example.mobileapi.entity.enums.OrderStatus;
import com.example.mobileapi.exception.AppException;
import com.example.mobileapi.service.DiscountService;
import com.example.mobileapi.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
@Tag(name = "Order", description = "Order API")
@FieldDefaults(makeFinal = true, level = lombok.AccessLevel.PRIVATE)
@PreAuthorize("hasRole('USER')")
public class OrderController {
    OrderService orderService;
    DiscountService discountService;

    @Operation(summary = "Lưu đơn hàng")
    @PostMapping
    public ApiResponse<UUID> createOrder(@RequestParam OrderMethod method,
                                         @RequestParam String discountCode,
                                         @RequestBody OrderRequestDTO orderRequestDTO) throws AppException {
        orderRequestDTO.setPaymentMethod(method);
        Integer discountPercent = discountService.getPercentDiscount(discountCode);
        orderRequestDTO.setTotalAmount(orderRequestDTO.getTotalAmount()
                .multiply(BigDecimal.valueOf(discountPercent))
                .divide(BigDecimal.valueOf(100))
        );
        return ApiResponse.<UUID>builder()
                .code(HttpStatus.OK.value())
                .data(orderService.saveOrder(orderRequestDTO))
                .build();
    }

    @Operation(summary = "Lấy danh sách đơn hàng theo ID khách hàng")
    @GetMapping("/customer/{customerId}")
    public ApiResponse<List<OrderResponseDTO>> getOrderByCustomerId(@PathVariable UUID customerId) {
        return ApiResponse.<List<OrderResponseDTO>>builder()
                .data(orderService.getOrderByCustomerId(customerId))
                .build();
    }

    @Operation(summary = "Lấy danh sách đơn hàng theo trạng thái và ID khách hàng")
    @GetMapping("/client/{status}&&{customerId}")
    public ApiResponse<List<OrderResponseDTO>> getOrderByStatusAndCustomerId(@PathVariable OrderStatus status, @PathVariable UUID customerId) {

        return ApiResponse.<List<OrderResponseDTO>>builder()
                .data(orderService.getOrdersByStatusAndCustomerId(status, customerId))
                .build();
    }
}

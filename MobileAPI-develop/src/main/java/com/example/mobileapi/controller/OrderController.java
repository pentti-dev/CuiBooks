package com.example.mobileapi.controller;

import com.example.mobileapi.entity.enums.OrderMethod;
import com.example.mobileapi.dto.request.OrderRequestDTO;
import com.example.mobileapi.dto.response.ApiResponse;
import com.example.mobileapi.dto.response.OrderResponseDTO;
import com.example.mobileapi.exception.AppException;
import com.example.mobileapi.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
@Tag(name = "Order", description = "Order API")
@FieldDefaults(makeFinal = true, level = lombok.AccessLevel.PRIVATE)
@PreAuthorize("hasRole('USER')")
public class OrderController {
    OrderService orderService;

    @Operation(summary = "Lưu đơn hàng")
    @PostMapping
    public ApiResponse<Integer> createOrder(@RequestParam OrderMethod method,
                                            @RequestBody OrderRequestDTO orderRequestDTO) throws AppException {
        orderRequestDTO.setPaymentMethod(method);

        return ApiResponse.<Integer>builder()
                .code(HttpStatus.OK.value())
                .data(orderService.saveOrder(orderRequestDTO))
                .build();
    }

    @Operation(summary = "Lấy danh sách đơn hàng theo ID khách hàng")
    @GetMapping("/customer/{customerId}")
    public ApiResponse<List<OrderResponseDTO>> getOrderByCustomerId(@PathVariable int customerId) {
        return ApiResponse.<List<OrderResponseDTO>>builder()
                .data(orderService.getOrderByCustomerId(customerId))
                .build();
    }

    @Operation(summary = "Lấy danh sách đơn hàng theo trạng thái và ID khách hàng")
    @GetMapping("/client/{status}&&{customerId}")
    public ApiResponse<List<OrderResponseDTO>> getOrderByStatusAndCustomerId(@PathVariable String status, @PathVariable int customerId) {

        return ApiResponse.<List<OrderResponseDTO>>builder()
                .data(orderService.getOrdersByStatusAndCustomerId(status, customerId))
                .build();
    }
}

package com.example.mobileapi.controller;

import com.example.mobileapi.dto.request.OrderDetailSaveRequest;
import com.example.mobileapi.dto.response.ApiResponse;
import com.example.mobileapi.dto.response.OrderDetailResponseDTO;
import com.example.mobileapi.exception.AppException;
import com.example.mobileapi.service.OrderDetailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order-detail")
@RequiredArgsConstructor
@Tag(name = "OrderDetail", description = "OrderDetail API")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderDetailController {
    OrderDetailService orderDetailService;

    @PostMapping
    public ApiResponse<OrderDetailResponseDTO> saveOrderDetail(OrderDetailSaveRequest orderDetailRequestDTO) throws AppException {
        return ApiResponse.<OrderDetailResponseDTO>builder()
                .data(orderDetailService.saveOrderDetail(orderDetailRequestDTO))
                .build();

    }

    @PutMapping("/{id}")
    public ApiResponse<OrderDetailResponseDTO> updateOrderDetail(@PathVariable int id, OrderDetailSaveRequest orderDetailRequestDTO) throws AppException {
        return ApiResponse.<OrderDetailResponseDTO>builder()
                .data(orderDetailService.updateOrderDetail(id, orderDetailRequestDTO))
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteOrderDetail(@PathVariable int id) {
        orderDetailService.deleteOrderDetail(id);
        return ApiResponse.success("Xóa chi tiết đơn hàng thành công!");
    }

    @GetMapping("/id/{id}")
    public ApiResponse<OrderDetailResponseDTO> getOrderDetailById(@PathVariable int id) {
        return ApiResponse.<OrderDetailResponseDTO>builder()
                .data(orderDetailService.findOrderDetailById(id))
                .build();

    }

    @GetMapping("/orderId/{orderId}")
    public ApiResponse<List<OrderDetailResponseDTO>> getOrderDetailByOrderId(@PathVariable int orderId) {
        return ApiResponse.<List<OrderDetailResponseDTO>>builder()
                .data(orderDetailService.findOrderDetailByOrderId(orderId))
                .build();
    }

    @GetMapping("/productId/{productId}")
    public ApiResponse<OrderDetailResponseDTO> getOrderDetailByProductId(@PathVariable int productId) {
        return ApiResponse.<OrderDetailResponseDTO>builder()
                .data(orderDetailService.findOrderDetailByProductId(productId))
                .build();

    }
}

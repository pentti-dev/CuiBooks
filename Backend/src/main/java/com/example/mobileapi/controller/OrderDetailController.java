package com.example.mobileapi.controller;

import com.example.mobileapi.dto.request.OrderDetailSaveRequest;
import com.example.mobileapi.dto.response.OrderDetailResponseDTO;
import com.example.mobileapi.service.OrderDetailService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order-detail")
@RequiredArgsConstructor
@Tag(name = "OrderDetail", description = "OrderDetail API")
public class OrderDetailController {
    private final OrderDetailService orderDetailService;

    @PostMapping
    public OrderDetailResponseDTO saveOrderDetail(OrderDetailSaveRequest orderDetailRequestDTO) {
        return orderDetailService.saveOrderDetail(orderDetailRequestDTO);
    }

    @PutMapping("/{id}")
    public OrderDetailResponseDTO updateOrderDetail(@PathVariable int id, OrderDetailSaveRequest orderDetailRequestDTO) {
        return orderDetailService.updateOrderDetail(id, orderDetailRequestDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteOrderDetail(@PathVariable int id) {
        orderDetailService.deleteOrderDetail(id);
    }

    @GetMapping("/id/{id}")
    public OrderDetailResponseDTO getOrderDetailById(@PathVariable int id) {
        return orderDetailService.findOrderDetailById(id);
    }

    @GetMapping("/orderId/{orderId}")
    public List<OrderDetailResponseDTO> getOrderDetailByOrderId(@PathVariable int orderId) {
        return orderDetailService.findOrderDetailByOrderId(orderId);
    }

    @GetMapping("/productId/{productId}")
    public OrderDetailResponseDTO getOrderDetailByProductId(@PathVariable int productId) {
        return orderDetailService.findOrderDetailByProductId(productId);
    }
}

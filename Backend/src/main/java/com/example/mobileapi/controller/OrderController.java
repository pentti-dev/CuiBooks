package com.example.mobileapi.controller;

import com.example.mobileapi.common.OrderMethod;
import com.example.mobileapi.common.OrderStatus;
import com.example.mobileapi.dto.request.OrderEditRequestDTO;
import com.example.mobileapi.dto.request.OrderRequestDTO;
import com.example.mobileapi.dto.response.MonthlyRevenueResponse;
import com.example.mobileapi.dto.response.OrderResponseDTO;
import com.example.mobileapi.service.OrderService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
@Tag(name = "Order", description = "Order API")
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    public int saveOrder(@RequestParam OrderMethod method, @RequestParam OrderStatus status, @RequestBody OrderRequestDTO orderRequestDTO) {
        orderRequestDTO.setPaymentMethod(method.getValue());
        orderRequestDTO.setStatus(status.getValue());
        return orderService.saveOrder(orderRequestDTO);
    }

    @PutMapping("/order/{orderId}")
    public void editOrder(@RequestBody OrderEditRequestDTO orderRequestDTO, @PathVariable("orderId") int orderId) {
        orderService.editOrder(orderId, orderRequestDTO);
    }

    @GetMapping("/customer/{customerId}")
    public List<OrderResponseDTO> getOrderByCustomerId(@PathVariable int customerId) {
        return orderService.getOrderByCustomerId(customerId);
    }

    @GetMapping("/list")
    public List<OrderResponseDTO> getAllOrders() {
        return orderService.getAllOrders();
    }

    @DeleteMapping("/{orderId}")
    public void deleteOrder(@PathVariable("orderId") int orderId) {
        orderService.deleteOrder(orderId);
    }

    @GetMapping("/revenue")
    public List<MonthlyRevenueResponse> getOrderRevenue() {
        return orderService.getMonthlyRevenue();
    }

    @GetMapping("/{status}")
    public List<OrderResponseDTO> getOrderByStatus(@PathVariable OrderStatus status) {
        return orderService.getOrdersByStatus(status.getValue());
    }

    @PutMapping("/status/{status}&&{orderId}")
    public ResponseEntity<String> changeOrderStatus(@PathVariable("status") OrderStatus status,
                                                    @PathVariable("orderId") int orderId) {
        try {
            orderService.changeOrderStatus(orderId, status.getValue());
            return ResponseEntity.ok("Cập nhật trạng thái thành công!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Lỗi: " + e.getMessage());
        }
    }

    @GetMapping("/client/{status}&&{customerId}")
    public List<OrderResponseDTO> getOrderByStatusAndCustomerId(@PathVariable String status, @PathVariable int customerId) {
        return orderService.getOrdersByStatusAndCustomerId(status, customerId);
    }
}

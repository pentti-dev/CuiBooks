package com.example.mobileapi.service;

import com.example.mobileapi.dto.request.OrderDetailRequestDTO;
import com.example.mobileapi.entity.enums.OrderStatus;
import com.example.mobileapi.dto.request.OrderEditRequestDTO;
import com.example.mobileapi.dto.request.OrderRequestDTO;
import com.example.mobileapi.dto.response.MonthlyRevenueResponse;
import com.example.mobileapi.dto.response.OrderResponseDTO;
import com.example.mobileapi.exception.AppException;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface OrderService {
    UUID saveOrder(OrderRequestDTO orderRequestDTO) throws AppException;

    // Tính toán tổng tiền đơn hàng từ chi tiết đơn hàng và mã giảm giá
    BigDecimal calcTotalAmount(List<OrderDetailRequestDTO> orderDetails, Integer discountPercent);

    OrderResponseDTO getOrder(UUID orderId);

    BigDecimal getPriceByOrderId(UUID orderId);

    void deleteOrder(UUID orderId);

    void updateOrder(UUID id, OrderRequestDTO orderRequestDTO);

    List<OrderResponseDTO> getOrderByCustomerId(UUID customerId);

    List<OrderResponseDTO> getAllOrders();

    void editOrder(UUID id, OrderEditRequestDTO orderEditRequestDTO) throws AppException;

    List<MonthlyRevenueResponse> getMonthlyRevenue();

    List<OrderResponseDTO> getOrdersByStatus(OrderStatus status);

    void changeOrderStatus(UUID orderId, OrderStatus status) throws AppException;

    List<OrderResponseDTO> getOrdersByStatusAndCustomerId(OrderStatus status, UUID customerId);

    boolean existById(UUID orderId);



}

package com.example.mobileapi.service;

import com.example.mobileapi.dto.request.OrderEditRequestDTO;
import com.example.mobileapi.dto.request.OrderRequestDTO;
import com.example.mobileapi.dto.response.MonthlyRevenueResponse;
import com.example.mobileapi.dto.response.OrderResponseDTO;

import java.util.List;

public interface OrderService {
    int saveOrder(OrderRequestDTO orderRequestDTO);

    OrderResponseDTO getOrder(int orderId);

    void deleteOrder(int orderId);

    void updateOrder(int id, OrderRequestDTO orderRequestDTO);

    List<OrderResponseDTO> getOrderByCustomerId(int customerId);

    List<OrderResponseDTO> getAllOrders();

    void editOrder(int id, OrderEditRequestDTO orderEditRequestDTO);

    List<MonthlyRevenueResponse> getMonthlyRevenue();

    List<OrderResponseDTO> getOrdersByStatus(String status);

    void changeOrderStatus(int orderId, String status);

    List<OrderResponseDTO> getOrdersByStatusAndCustomerId(String status, int customerId);
}

package com.example.mobileapi.service;

import com.example.mobileapi.entity.Order;
import com.example.mobileapi.entity.enums.OrderStatus;
import com.example.mobileapi.dto.request.CustomerRequestDTO;
import com.example.mobileapi.dto.request.OrderEditRequestDTO;
import com.example.mobileapi.dto.response.CustomerResponseDTO;
import com.example.mobileapi.dto.response.MonthlyRevenueResponse;
import com.example.mobileapi.dto.response.OrderResponseDTO;
import com.example.mobileapi.exception.AppException;

import java.util.List;
import java.util.UUID;

public interface AdminService {
    List<CustomerResponseDTO> getAllCustomers();

    CustomerResponseDTO getCustomerById(UUID customerId);

    CustomerResponseDTO addCustomer(CustomerRequestDTO customer) throws AppException;

    CustomerResponseDTO updateCustomer(UUID customerId, CustomerRequestDTO customer) throws AppException;

    void deleteCustomer(UUID customerId);

    List<OrderResponseDTO> getAllOrders();

    void deleteOrder(UUID orderId);

    void editOrder(UUID orderId, OrderEditRequestDTO dto) throws AppException;

    List<OrderResponseDTO> getOrdersByStatus(OrderStatus status);

    void changeOrderStatus(UUID orderId, OrderStatus status) throws AppException;

    List<MonthlyRevenueResponse> getMonthlyRevenue();

}

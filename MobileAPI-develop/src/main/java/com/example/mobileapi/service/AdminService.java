package com.example.mobileapi.service;

import com.example.mobileapi.entity.enums.OrderStatus;
import com.example.mobileapi.dto.request.CustomerRequestDTO;
import com.example.mobileapi.dto.request.OrderEditRequestDTO;
import com.example.mobileapi.dto.response.CustomerResponseDTO;
import com.example.mobileapi.dto.response.MonthlyRevenueResponse;
import com.example.mobileapi.dto.response.OrderResponseDTO;
import com.example.mobileapi.exception.AppException;

import java.util.List;

public interface AdminService {
    List<CustomerResponseDTO> getAllCustomers();

    CustomerResponseDTO getCustomerById(int customerId);

    CustomerResponseDTO addCustomer(CustomerRequestDTO customer) throws AppException;

    CustomerResponseDTO updateCustomer(int customerId, CustomerRequestDTO customer) throws AppException;

    void deleteCustomer(int customerId);

    List<OrderResponseDTO> getAllOrders();

    void deleteOrder(int orderId);

    void editOrder(int orderId, OrderEditRequestDTO dto) throws AppException;

    List<OrderResponseDTO> getOrdersByStatus(String status);

    void changeOrderStatus(int orderId, OrderStatus status) throws AppException;

    List<MonthlyRevenueResponse> getMonthlyRevenue();

}

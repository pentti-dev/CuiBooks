package com.example.mobileapi.service.impl;

import com.example.mobileapi.entity.enums.OrderStatus;
import com.example.mobileapi.dto.request.CustomerRequestDTO;
import com.example.mobileapi.dto.request.OrderEditRequestDTO;
import com.example.mobileapi.dto.response.CustomerResponseDTO;
import com.example.mobileapi.dto.response.MonthlyRevenueResponse;
import com.example.mobileapi.dto.response.OrderResponseDTO;
import com.example.mobileapi.exception.AppException;
import com.example.mobileapi.service.AdminService;
import com.example.mobileapi.service.CustomerService;
import com.example.mobileapi.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = lombok.AccessLevel.PRIVATE)
@Service
public class AdminServiceImpl implements AdminService {
    CustomerService customerService;

    @Override
    public List<CustomerResponseDTO> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    @Override
    public CustomerResponseDTO getCustomerById(int customerId) {
        return customerService.getCustomer(customerId);
    }

    @Override
    public CustomerResponseDTO addCustomer(CustomerRequestDTO customer) throws AppException {
        return customerService.saveCustomer(customer);
    }

    @Override
    public CustomerResponseDTO updateCustomer(int customerId, CustomerRequestDTO customer) throws AppException {
        return customerService.updateCustomer(customerId, customer);
    }

    @Override
    public void deleteCustomer(int customerId) {
        customerService.deleteCustomer(customerId);

    }

    OrderService orderService;

    @Override
    public List<OrderResponseDTO> getAllOrders() {
        return orderService.getAllOrders();
    }

    @Override
    public void deleteOrder(int orderId) {
        orderService.deleteOrder(orderId);

    }

    @Override
    public void editOrder(int orderId, OrderEditRequestDTO dto) throws AppException {
        orderService.editOrder(orderId, dto);

    }

    @Override
    public List<OrderResponseDTO> getOrdersByStatus(String status) {
        return orderService.getOrdersByStatus(status);
    }

    @Override
    public void changeOrderStatus(int orderId, OrderStatus status) throws AppException {
        orderService.changeOrderStatus(orderId, status);

    }

    @Override
    public List<MonthlyRevenueResponse> getMonthlyRevenue() {
        return orderService.getMonthlyRevenue();
    }


}

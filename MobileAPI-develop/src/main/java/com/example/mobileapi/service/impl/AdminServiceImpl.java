package com.example.mobileapi.service.impl;

import com.example.mobileapi.entity.Product;
import com.example.mobileapi.entity.enums.OrderStatus;
import com.example.mobileapi.dto.request.CustomerRequestDTO;
import com.example.mobileapi.dto.request.OrderEditRequestDTO;
import com.example.mobileapi.dto.response.CustomerResponseDTO;
import com.example.mobileapi.dto.response.MonthlyRevenueResponse;
import com.example.mobileapi.dto.response.OrderResponseDTO;
import com.example.mobileapi.exception.AppException;
import com.example.mobileapi.service.AdminService;
import com.example.mobileapi.service.CategoryService;
import com.example.mobileapi.service.CustomerService;
import com.example.mobileapi.service.OrderService;
import com.example.mobileapi.service.ProductService;

import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;
import lombok.experimental.FieldDefaults;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = lombok.AccessLevel.PRIVATE)
@Service
@Primary
public class AdminServiceImpl implements AdminService {
    @Delegate
    ProductService productService;
    @Delegate
    CustomerService customerService;
    @Delegate
    OrderService orderService;
    @Delegate
    CategoryService categoryService;

}

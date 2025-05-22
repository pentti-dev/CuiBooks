package com.example.mobileapi.service.impl;

import com.example.mobileapi.service.AdminService;
import com.example.mobileapi.service.CategoryService;
import com.example.mobileapi.service.CustomerService;
import com.example.mobileapi.service.DiscountService;
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
    @Delegate
    DiscountService discountService;

}

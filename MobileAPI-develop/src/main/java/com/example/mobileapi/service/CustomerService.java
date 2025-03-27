package com.example.mobileapi.service;


import com.example.mobileapi.dto.request.CustomerRequestDTO;
import com.example.mobileapi.dto.request.IntrospectRequest;
import com.example.mobileapi.dto.request.LoginRequest;
import com.example.mobileapi.dto.response.CustomerResponseDTO;
import com.example.mobileapi.dto.response.IntrospectResponse;
import com.example.mobileapi.dto.response.LoginResponse;
import com.example.mobileapi.exception.AppException;

import java.util.List;

public interface CustomerService {
    int saveCustomer(CustomerRequestDTO request);


    void deleteCustomer(int customerId);

    CustomerResponseDTO getCustomer(int customerId);

    List<CustomerResponseDTO> getAllCustomers();

    boolean checkUsername(String username);

    boolean checkEmail(String email);


    CustomerResponseDTO updateCustomerById(int customerId, CustomerRequestDTO request);


    void resetPassword(String username, String resetCode, String newPassword);

    void initPasswordReset(String username);

    int getQuantityByCustomerId(int customerId);

    void changePassword(int customerId, String oldPassword, String newPassword) throws AppException;


    CustomerResponseDTO getCustomerProfile(String token);
}

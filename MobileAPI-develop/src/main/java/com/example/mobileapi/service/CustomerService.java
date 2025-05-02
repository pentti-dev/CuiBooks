package com.example.mobileapi.service;


import com.example.mobileapi.dto.request.CustomerRequestDTO;
import com.example.mobileapi.dto.response.CustomerResponseDTO;
import com.example.mobileapi.exception.AppException;

import java.util.List;
import java.util.UUID;

public interface CustomerService {
    CustomerResponseDTO saveCustomer(CustomerRequestDTO request) throws AppException;


    void deleteCustomer(UUID customerId);

    CustomerResponseDTO getCustomer(UUID customerId);

    List<CustomerResponseDTO> getAllCustomers();

    boolean checkUsername(String username);

    boolean checkEmail(String email);


    CustomerResponseDTO updateCustomer(UUID customerId, CustomerRequestDTO request) throws AppException;


    void resetPassword(String username, String resetCode, String newPassword) throws AppException;

    void initPasswordReset(String username) throws AppException;

    int getQuantityByCustomerId(UUID customerId);

    void changePassword(UUID customerId, String oldPassword, String newPassword) throws AppException;


    CustomerResponseDTO getCustomerProfile(String token) throws AppException;

}

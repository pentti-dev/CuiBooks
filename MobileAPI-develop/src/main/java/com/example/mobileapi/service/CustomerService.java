package com.example.mobileapi.service;


import com.example.mobileapi.dto.request.CustomerRequestDTO;
import com.example.mobileapi.dto.request.CustomerUpdateRequestDTO;
import com.example.mobileapi.dto.response.CustomerResponseDTO;

import java.util.List;

public interface CustomerService {
    int saveCustomer(CustomerRequestDTO request);

    void updateCustomer(int customerId, CustomerRequestDTO request);

    void deleteCustomer(int customerId);

    CustomerResponseDTO getCustomer(int customerId);

    List<CustomerResponseDTO> getAllCustomers();

    boolean checkUsername(String username);

    CustomerResponseDTO login(String username, String password);

    CustomerResponseDTO updateCustomerById(int customerId, CustomerUpdateRequestDTO request);

    void updateByAdmin(int customerId, CustomerRequestDTO customerRequestDTO);

    void resetPassword(String username, String resetCode, String newPassword);

    void initPasswordReset(String username);

    int getQuantityByCustomerId(int customerId);

    void changePassword(int customerId, String oldPassword, String newPassword);
}

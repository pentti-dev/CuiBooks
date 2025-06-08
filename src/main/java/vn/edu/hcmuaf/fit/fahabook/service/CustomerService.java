package vn.edu.hcmuaf.fit.fahabook.service;

import java.util.List;
import java.util.UUID;

import vn.edu.hcmuaf.fit.fahabook.dto.request.CustomerRequestDTO;
import vn.edu.hcmuaf.fit.fahabook.dto.response.CustomerResponseDTO;
import vn.edu.hcmuaf.fit.fahabook.entity.Customer;
import vn.edu.hcmuaf.fit.fahabook.exception.AppException;

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

    Customer findByEmailAndCreate(String email, String fullname);
}

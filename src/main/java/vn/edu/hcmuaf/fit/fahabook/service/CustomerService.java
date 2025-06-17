package vn.edu.hcmuaf.fit.fahabook.service;

import vn.edu.hcmuaf.fit.fahabook.dto.request.create.CreateCustomerDTO;
import vn.edu.hcmuaf.fit.fahabook.dto.request.update.UpdateCustomerDTO;
import vn.edu.hcmuaf.fit.fahabook.dto.response.CustomerResponseDTO;
import vn.edu.hcmuaf.fit.fahabook.entity.Customer;
import vn.edu.hcmuaf.fit.fahabook.entity.enums.CustomerStatus;
import vn.edu.hcmuaf.fit.fahabook.exception.AppException;

import java.util.List;
import java.util.UUID;

public interface CustomerService {
    CustomerResponseDTO saveCustomer(CreateCustomerDTO request) throws AppException;

    void deleteCustomer(UUID customerId);

    void changeStatusById(UUID customerId, CustomerStatus status);

    CustomerResponseDTO getCustomer(UUID customerId);

    List<CustomerResponseDTO> getAllCustomers();

    boolean checkUsername(String username);

    boolean checkEmail(String email);

    CustomerResponseDTO updateCustomer(UUID customerId, UpdateCustomerDTO request) throws AppException;

    void resetPassword(String username, String resetCode, String newPassword) throws AppException;

    void initPasswordReset(String username) throws AppException;

    int getQuantityByCustomerId(UUID customerId);

    void changePassword(UUID customerId, String oldPassword, String newPassword) throws AppException;

    CustomerResponseDTO getCustomerProfile(String token) throws AppException;

    Customer findByEmailAndCreate(String email, String fullname);
}

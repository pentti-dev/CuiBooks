package com.example.mobileapi.service.impl;

import com.example.mobileapi.config.BCryptPasswordEncoder;
import com.example.mobileapi.dto.request.CustomerRequestDTO;
import com.example.mobileapi.dto.response.CustomerResponseDTO;
import com.example.mobileapi.exception.AppException;
import com.example.mobileapi.exception.ErrorCode;
import com.example.mobileapi.mapper.CustomerMapper;
import com.example.mobileapi.model.Customer;
import com.example.mobileapi.repository.CustomerRepository;
import com.example.mobileapi.service.CustomerService;
import com.example.mobileapi.service.EmailService;
import com.example.mobileapi.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = lombok.AccessLevel.PRIVATE)
public class CustomerServiceImpl implements CustomerService {

    CustomerRepository customerRepository;
    EmailService emailService;
    BCryptPasswordEncoder passwordEncoder; // Đổi tên cho rõ ràng
    JwtUtil jwtUtil;
    CustomerMapper customerMapper;
    Random random = new Random();

    @Override
    public int saveCustomer(CustomerRequestDTO request) {
        Customer customer = Customer.builder()
                .fullname(request.getFullname())
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword())) // Sử dụng passwordEncoder
                .email(request.getEmail())
                .phone(request.getPhone())
                .build();
        return customerRepository.save(customer).getId();
    }


    @Override
    public void deleteCustomer(int customerId) {
        customerRepository.deleteById(customerId);
    }

    @Override
    public CustomerResponseDTO getCustomer(int customerId) {
        Customer customer = getCustomerById(customerId);

        return customerMapper.toCustomerResponse(customer);
    }

    @Override
    public List<CustomerResponseDTO> getAllCustomers() {
        log.info("Get all customers");
        List<Customer> customers = customerRepository.findAll();
        return customers
                .stream()
                .map(customerMapper::toCustomerResponse)
                .toList();
    }

    @Override
    public boolean checkUsername(String username) {
        return customerRepository.existsByUsername(username);
    }

    @Override
    public boolean checkEmail(String email) {
        return customerRepository.existsByEmail(email).isPresent();
    }


    @Override
    public CustomerResponseDTO updateCustomerById(int customerId, CustomerRequestDTO request) throws AppException {

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        customer.setFullname(request.getFullname());
        customer.setEmail(request.getEmail());
        customer.setPhone(request.getPhone());
        customerRepository.save(customer);
        return customerMapper.toCustomerResponse(customer);
    }


    @Override
    public void resetPassword(String username, String resetCode, String newPassword) throws AppException {
        Customer customer = getCustomerByUserName(username);
        if (customer != null && resetCode.equals(customer.getResetCode())) {
            customer.setPassword(passwordEncoder.encode(newPassword)); // Sử dụng passwordEncoder
            customer.setResetCode(null); // Xóa mã reset sau khi đặt lại mật khẩu thành công
            customerRepository.save(customer);
            emailService.sendPasswordResetEmail(
                    customer.getEmail(),
                    "Xác nhận Đặt Lại Mật Khẩu",
                    "Mật khẩu của bạn đã được đặt lại thành công.");
        } else {
            throw new AppException(ErrorCode.INVALID_RESET_CODE);
        }
    }

    @Override
    public void initPasswordReset(String username) throws AppException {
        Customer customer = customerRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        if (customer != null) {
            String resetCode = generateResetCode();
            customer.setResetCode(resetCode);
            customerRepository.save(customer);

            // Gửi email chứa mã resetCode đến email của khách hàng
            emailService.sendPasswordResetEmail(
                    customer.getEmail(),
                    "Yêu cầu Đặt Lại Mật Khẩu",
                    "Mã xác nhận đặt lại mật khẩu của bạn là: " + resetCode);
        } else {
            throw new AppException(ErrorCode.USER_NOT_FOUND);
        }
    }

    @Override
    public int getQuantityByCustomerId(int customerId) {
        return customerRepository.getQuantityByCustomerId(customerId);
    }

    @Override
    public void changePassword(int customerId, String oldPassword, String newPassword) throws AppException {
        Customer customer = getCustomerById(customerId);
        if (passwordEncoder.matches(oldPassword, customer.getPassword())) { // Sử dụng passwordEncoder
            customer.setPassword(passwordEncoder.encode(newPassword)); // Sử dụng passwordEncoder
            customerRepository.save(customer);
        } else {
            throw new AppException(ErrorCode.WRONG_OLD_PASSWORD);
        }
    }


    @Override
    @PostAuthorize("returnObject.username ==authentication.name")
    public CustomerResponseDTO getCustomerProfile(String token) throws AppException {
        String username = jwtUtil.getUserNameFormToken(token);
        return customerMapper.toCustomerResponse(getCustomerByUserName(username));

    }


    Customer getCustomerByUserName(String username) throws AppException {
        return customerRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
    }

    private String generateResetCode() {
        // Tạo một mã ngẫu nhiên chứa các ký tự chữ và số
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < 6; i++) {
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }
        return sb.toString();
    }

    public Customer getCustomerById(int customerId) {
        try {
            return customerRepository.findById(customerId).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        } catch (AppException e) {
            throw new RuntimeException(e);
        }
    }
}

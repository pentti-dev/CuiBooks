package com.example.mobileapi.service.impl;

import com.example.mobileapi.config.BCryptPasswordEncoder;
import com.example.mobileapi.dto.request.CustomerRequestDTO;
import com.example.mobileapi.dto.request.CustomerUpdateRequestDTO;
import com.example.mobileapi.dto.response.CustomerResponseDTO;
import com.example.mobileapi.model.Customer;
import com.example.mobileapi.repository.CustomerRepository;
import com.example.mobileapi.service.CustomerService;
import com.example.mobileapi.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@Transactional
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final EmailService emailService;
    private final BCryptPasswordEncoder passwordEncoder; // Đổi tên cho rõ ràng

    public CustomerServiceImpl(CustomerRepository customerRepository, EmailService emailService, BCryptPasswordEncoder passwordEncoder) {
        this.customerRepository = customerRepository;
        this.emailService = emailService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public int saveCustomer(CustomerRequestDTO request) {
        Customer customer = Customer.builder()
                .fullname(request.getFullname())
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword())) // Sử dụng passwordEncoder
                .email(request.getEmail())
                .phone(request.getPhone())
                .build();
        Integer id = customerRepository.save(customer).getId();
        return id;
    }

    @Override
    public void updateCustomer(int customerId, CustomerRequestDTO request) {
        Customer customer = getCustomerById(customerId);
        customer.setFullname(request.getFullname());
        customer.setUsername(request.getUsername());
        customer.setPassword(passwordEncoder.encode(request.getPassword())); // Sử dụng passwordEncoder
        customer.setEmail(request.getEmail());
        customer.setPhone(request.getPhone());
        customerRepository.save(customer);
    }

    @Override
    public void deleteCustomer(int customerId) {
        customerRepository.deleteById(customerId);
    }

    @Override
    public CustomerResponseDTO getCustomer(int customerId) {
        Customer customer = getCustomerById(customerId);
        if (customer == null) {
            return null;
        }
        return CustomerResponseDTO.builder()
                .username(customer.getUsername())
                .phone(customer.getPhone())
                .email(customer.getEmail())
                .fullname(customer.getFullname())
                .id(customer.getId())
                .role(customer.isRole())
                .build();
    }

    @Override
    public List<CustomerResponseDTO> getAllCustomers() {
        List<Customer> customers = customerRepository.findAll();
        List<CustomerResponseDTO> customerResponseDTOS = new ArrayList<>();
        for (Customer customer : customers) {
            customerResponseDTOS.add(CustomerResponseDTO.builder()
                    .fullname(customer.getFullname())
                    .username(customer.getUsername())
                    .phone(customer.getPhone())
                    .email(customer.getEmail())
                    .id(customer.getId())
                    .role(customer.isRole())
                    .build());
        }
        return customerResponseDTOS;
    }

    @Override
    public boolean checkUsername(String username) {
        return customerRepository.existsByUsername(username);
    }

    @Override
    public CustomerResponseDTO login(String username, String password) {
        log.warn("Username: " + username + " Password: " + password);
        Customer customer = customerRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("Không tìm thấy customer"));
        if (passwordEncoder.matches(password, customer.getPassword())) { // Sử dụng passwordEncoder
            return CustomerResponseDTO.builder()
                    .fullname(customer.getFullname())
                    .username(customer.getUsername())
                    .email(customer.getEmail())
                    .phone(customer.getPhone())
                    .id(customer.getId())
                    .role(customer.isRole())
                    .build();
        } else {
            throw new IllegalArgumentException("Sai mật khẩu");
        }
    }

    @Override
    public CustomerResponseDTO updateCustomerById(int customerId, CustomerUpdateRequestDTO request) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy customer"));
        customer.setFullname(request.getName());
        customer.setEmail(request.getEmail());
        customer.setPhone(request.getPhone());
        customerRepository.save(customer);
        return CustomerResponseDTO.builder()
                .fullname(customer.getFullname())
                .username(customer.getUsername())
                .email(customer.getEmail())
                .phone(customer.getPhone())
                .id(customer.getId())
                .role(customer.isRole())
                .build();
    }

    @Override
    public void updateByAdmin(int customerId, CustomerRequestDTO request) {
        Customer customer = getCustomerById(customerId);
        customer.setFullname(request.getFullname());
        customer.setEmail(request.getEmail());
        customer.setPhone(request.getPhone());
        customerRepository.save(customer);
    }

    @Override
    public void resetPassword(String username, String resetCode, String newPassword) {
        Customer customer = getCustomerByName(username);
        if (customer != null && resetCode.equals(customer.getResetCode())) {
            customer.setPassword(passwordEncoder.encode(newPassword)); // Sử dụng passwordEncoder
            customer.setResetCode(null); // Xóa mã reset sau khi đặt lại mật khẩu thành công
            customerRepository.save(customer);
            emailService.sendPasswordResetEmail(
                    customer.getEmail(),
                    "Xác nhận Đặt Lại Mật Khẩu",
                    "Mật khẩu của bạn đã được đặt lại thành công."
            );
        } else {
            throw new IllegalArgumentException("Mã reset không hợp lệ hoặc đã hết hạn.");
        }
    }

    @Override
    public void initPasswordReset(String username) {
        Customer customer = customerRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("Không tìm thấy customer"));
        if (customer != null) {
            String resetCode = generateResetCode();
            customer.setResetCode(resetCode);
            customerRepository.save(customer);

            // Gửi email chứa mã resetCode đến email của khách hàng
            emailService.sendPasswordResetEmail(
                    customer.getEmail(),
                    "Yêu cầu Đặt Lại Mật Khẩu",
                    "Mã xác nhận đặt lại mật khẩu của bạn là: " + resetCode
            );
        } else {
            throw new IllegalArgumentException("Không tìm thấy khách hàng với username: " + username);
        }
    }

    @Override
    public int getQuantityByCustomerId(int customerId) {
        return customerRepository.getQuantityByCustomerId(customerId);
    }

    @Override
    public void changePassword(int customerId, String oldPassword, String newPassword) {
        Customer customer = getCustomerById(customerId);
        if (passwordEncoder.matches(oldPassword, customer.getPassword())) { // Sử dụng passwordEncoder
            customer.setPassword(passwordEncoder.encode(newPassword)); // Sử dụng passwordEncoder
            customerRepository.save(customer);
        } else {
            throw new IllegalArgumentException("Mật khẩu cũ không chính xác");
        }
    }

    Customer getCustomerByName(String username) {
        return customerRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("Không tìm thấy customer"));
    }

    private String generateResetCode() {
        // Tạo một mã ngẫu nhiên chứa các ký tự chữ và số
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder();
        java.util.Random random = new java.util.Random();
        for (int i = 0; i < 6; i++) {
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }
        return sb.toString();
    }

    public Customer getCustomerById(int customerId) {
        return customerRepository.findById(customerId).orElse(null);
    }
}

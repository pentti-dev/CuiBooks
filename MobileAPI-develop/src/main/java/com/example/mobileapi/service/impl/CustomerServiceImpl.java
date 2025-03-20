package com.example.mobileapi.service.impl;

import com.example.mobileapi.config.BCryptPasswordEncoder;
import com.example.mobileapi.dto.request.CustomerRequestDTO;
import com.example.mobileapi.dto.request.CustomerUpdateRequestDTO;
import com.example.mobileapi.dto.request.IntrospectRequest;
import com.example.mobileapi.dto.request.LoginRequest;
import com.example.mobileapi.dto.response.CustomerResponseDTO;
import com.example.mobileapi.dto.response.IntrospectResponse;
import com.example.mobileapi.dto.response.LoginResponse;
import com.example.mobileapi.mapper.CustomerMapper;
import com.example.mobileapi.model.Customer;
import com.example.mobileapi.model.enums.Role;
import com.example.mobileapi.repository.CartRepository;
import com.example.mobileapi.repository.CustomerRepository;
import com.example.mobileapi.service.CustomerService;
import com.example.mobileapi.service.EmailService;
import com.example.mobileapi.util.JwtUtil;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jwt.SignedJWT;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

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
    CartRepository cartRepository;

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

    @PostAuthorize("returnObject.username ==authentication.name")
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
                .role(Role.role(customer.isRole()))
                .build();
    }

    @PreAuthorize("hasRole('ADMIN')")
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
        return customerRepository.findCustomerByEmail(email).isPresent();
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        log.warn("Username: " + loginRequest.getUsername() + " Password: " + loginRequest.getPassword());
        Customer customer = customerRepository.findByUsername(loginRequest.getUsername())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy customer"));
        if (passwordEncoder.matches(loginRequest.getPassword(), customer.getPassword())) { // Sử dụng passwordEncoder

            var token = jwtUtil.generateToken(customer);
            return LoginResponse.builder()
                    .token(token)
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
                .role(Role.role(customer.isRole()))
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
                    "Mật khẩu của bạn đã được đặt lại thành công.");
        } else {
            throw new IllegalArgumentException("Mã reset không hợp lệ hoặc đã hết hạn.");
        }
    }

    @Override
    public void initPasswordReset(String username) {
        Customer customer = customerRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy customer"));
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

    @Override
    public IntrospectResponse introspect(IntrospectRequest request) {
        try {
            JWSVerifier verifier = new RSASSAVerifier(jwtUtil.loadPublicKey());

            var token = request.getToken();
            SignedJWT signedJWT = SignedJWT.parse(token);

            Date expirationTime = signedJWT.getJWTClaimsSet().getExpirationTime();

            var verify = signedJWT.verify(verifier);

            return IntrospectResponse.builder()
                    .valid(verify && expirationTime.after(new Date()))
                    .build();
        } catch (JOSEException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    Customer getCustomerByName(String username) {
        return customerRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy customer"));
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

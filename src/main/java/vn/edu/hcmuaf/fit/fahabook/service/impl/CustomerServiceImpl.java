package vn.edu.hcmuaf.fit.fahabook.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import vn.edu.hcmuaf.fit.fahabook.config.BCryptPasswordEncoder;
import vn.edu.hcmuaf.fit.fahabook.dto.request.CustomerRequestDTO;
import vn.edu.hcmuaf.fit.fahabook.dto.response.CustomerResponseDTO;
import vn.edu.hcmuaf.fit.fahabook.entity.Customer;
import vn.edu.hcmuaf.fit.fahabook.entity.enums.CustomerStatus;
import vn.edu.hcmuaf.fit.fahabook.entity.enums.Role;
import vn.edu.hcmuaf.fit.fahabook.event.CustomerCreatedEvent;
import vn.edu.hcmuaf.fit.fahabook.exception.AppException;
import vn.edu.hcmuaf.fit.fahabook.exception.ErrorCode;
import vn.edu.hcmuaf.fit.fahabook.mapper.CustomerMapper;
import vn.edu.hcmuaf.fit.fahabook.repository.CustomerRepository;
import vn.edu.hcmuaf.fit.fahabook.service.CustomerService;
import vn.edu.hcmuaf.fit.fahabook.service.EmailService;
import vn.edu.hcmuaf.fit.fahabook.util.JwtUtil;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = lombok.AccessLevel.PRIVATE)
public class CustomerServiceImpl implements CustomerService {

    CustomerRepository customerRepository;
    EmailService emailService;
    BCryptPasswordEncoder passwordEncoder; // Đổi tên cho rõ ràng
    CustomerMapper customerMapper;
    Random random = new Random();
    ApplicationEventPublisher applicationEventPublisher;

    @Override
    @Transactional
    public CustomerResponseDTO saveCustomer(CustomerRequestDTO dto) throws AppException {
        // Mã hóa mật khẩu trước khi lưu vào cơ sở dữ liệu
        dto.setPassword(passwordEncoder.encode(dto.getPassword())); // Sử dụng passwordEncoder
        dto.setRole(Role.USER);
        dto.setStatus(CustomerStatus.ACTIVE);
        Customer customer = customerMapper.toCustomer(dto);
        Customer customerSaved = customerRepository.saveAndFlush(customer);
        log.warn(
                "Customer save  ID: {}, username: {}, email: {}, phone: {}",
                customerSaved.getId(),
                customerSaved.getUsername(),
                customerSaved.getEmail(),
                customerSaved.getPhone()
        );
        log.info("Customer created with ID: {}", customer.getId());
        applicationEventPublisher.publishEvent(new CustomerCreatedEvent(this, customer.getId()));
        return customerMapper.toCustomerResponse(customerSaved);
    }

    @Override
    public void deleteCustomer(UUID customerId) {
        changeStatusById(customerId, CustomerStatus.DELETED);
    }

    @Override
    public void changeStatusById(UUID customerId, CustomerStatus status) {
        Customer customer = getCustomerById(customerId);
        customer.setStatus(status);
        customerRepository.save(customer);
    }

    @Override
    public CustomerResponseDTO getCustomer(UUID customerId) {
        Customer customer = getCustomerById(customerId);

        return customerMapper.toCustomerResponse(customer);
    }

    @Override
    public List<CustomerResponseDTO> getAllCustomers() {
        log.info("Get all customers");
        List<Customer> customers = customerRepository.findAll();
        return customers.stream().map(customerMapper::toCustomerResponse).toList();
    }

    @Override
    public boolean checkUsername(String username) {
        return customerRepository.existsByUsername(username);
    }

    @Override
    public boolean checkEmail(String email) {
        return customerRepository.existsByEmail(email);
    }

    @Override
    @Transactional
    public CustomerResponseDTO updateCustomer(UUID customerId, CustomerRequestDTO request) throws AppException {
        Customer customer =
                customerRepository.findById(customerId).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        log.info("Role before update : {}", request.getRole());
        if (request.getRole() != null) {
            if (!isCurrentUserAdmin() && request.getRole() != Role.USER) {
                throw new AppException(ErrorCode.FORBIDDEN);
            }
            customer.setRole(request.getRole());

        } else if (request.getPassword() != null) {

            request.setPassword(passwordEncoder.encode(request.getPassword()));
        }
        request.setRole(Role.USER);
        // Cập nhật thông tin từ DTO
        customerMapper.updateCustomerFromDto(request, customer);
        log.warn("Role after update  : {}  ", customer.getRole());
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
                    customer.getEmail(), "Xác nhận Đặt Lại Mật Khẩu", "Mật khẩu của bạn đã được đặt lại thành công.");
        } else {
            throw new AppException(ErrorCode.INVALID_RESET_CODE);
        }
    }

    @Override
    public void initPasswordReset(String username) throws AppException {
        Customer customer = customerRepository
                .findByUsername(username)
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
    public int getQuantityByCustomerId(UUID customerId) {
        return customerRepository.getQuantityByCustomerId(customerId);
    }

    @Override
    public void changePassword(UUID customerId, String oldPassword, String newPassword) throws AppException {
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
        String username = JwtUtil.getUserNameFromToken(token);
        return customerMapper.toCustomerResponse(getCustomerByUserName(username));
    }

    @Override
    @Transactional
    public Customer findByEmailAndCreate(String email, String fullname) {
        Optional<Customer> customer = customerRepository.findByEmail(email);
        if (customer.isPresent()) {
            return customer.get();
        }


        Customer saved = customerRepository.saveAndFlush(Customer.builder()
                .email(email)
                .username(email)
                .fullname(fullname)
                .password(passwordEncoder.encode(email))
                .role(Role.USER)
                .build());
        applicationEventPublisher.publishEvent(new CustomerCreatedEvent(this, saved.getId()));

        return saved;
    }

    private boolean isCurrentUserAdmin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null
                && authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
    }

    Customer getCustomerByUserName(String username) throws AppException {
        return customerRepository
                .findByUsername(username)
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

    public Customer getCustomerById(UUID customerId) throws AppException {
        return customerRepository.findById(customerId).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
    }

    public UUID getCustomerIdByUsername(String username) {
        return customerRepository.findIdByUsername(username);
    }
}

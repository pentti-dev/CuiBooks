package com.example.mobileapi.controller;

import com.example.mobileapi.dto.request.*;
import com.example.mobileapi.dto.response.ApiResponse;
import com.example.mobileapi.dto.response.CustomerResponseDTO;
import com.example.mobileapi.dto.response.IntrospectResponse;
import com.example.mobileapi.dto.response.LoginResponse;
import com.example.mobileapi.exception.AppException;
import com.example.mobileapi.exception.ErrorCode;
import com.example.mobileapi.service.CartService;
import com.example.mobileapi.service.CustomerService;
import com.example.mobileapi.service.impl.CustomerServiceImpl;
import com.example.mobileapi.util.JwtUtil;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customer")
@RequiredArgsConstructor
@Tag(name = "Customer", description = "Customer API")
@FieldDefaults(makeFinal = true, level = lombok.AccessLevel.PRIVATE)
public class CustomerController {
    Logger log = LoggerFactory.getLogger(CustomerController.class);
    CustomerService customerService;
    CartService cartService;
    CustomerServiceImpl customerServiceImpl;
    JwtUtil jwtUtil;

    @GetMapping("/quantity/{customerId}")
    public ApiResponse<Integer> getQuantity(@PathVariable("customerId") int customerId) {
//        return customerService.getQuantityByCustomerId(customerId);
        return ApiResponse.<Integer>builder().data(customerService.getQuantityByCustomerId(customerId)).build();

    }

    @PostMapping
    public ApiResponse addCustomer(@RequestBody @Valid CustomerRequestDTO customer) throws AppException {
        if (customerService.checkUsername(customer.getUsername())) {
            throw new AppException(ErrorCode.USERNAME_EXISTED);
        } else if (customerService.checkEmail(customer.getEmail())) {
            throw new AppException(ErrorCode.EMAIL_EXISTED);
        }
        int userId = customerService.saveCustomer(customer);
        CartRequestDTO cartRequestDTO = new CartRequestDTO();
        cartRequestDTO.setCustomerId(userId);
        cartService.saveCart(cartRequestDTO);
        return ApiResponse.builder().code(2000).message("Thêm người dùng thành công").build();
    }

    @PutMapping("/{customerId}")
    public ApiResponse updateCustomer(@PathVariable int customerId, @RequestBody CustomerRequestDTO customer) throws AppException {
        customerService.updateCustomer(customerId, customer);
        return ApiResponse.builder().code(2000).message("Cập nhật người dùng thành công").build();
    }

    @PutMapping("/admin/{customerId}")
    public ApiResponse updateAdmin(@PathVariable int customerId, @RequestBody CustomerRequestDTO customer) throws AppException {
        customerService.updateByAdmin(customerId, customer);

        return ApiResponse.builder().code(2000).message("Cập nhật người dùng thành công").build();
    }

    @DeleteMapping("/{customerId}")
    public ApiResponse deleteCustomer(@PathVariable int customerId) {
        customerService.deleteCustomer(customerId);
        return ApiResponse.builder().code(2000).message("Xóa người dùng thành công").build();
    }

    @GetMapping("/{customerId}")
    public ApiResponse<CustomerResponseDTO> getCustomer(@PathVariable int customerId) {
        return ApiResponse.<CustomerResponseDTO>builder().data(customerService.getCustomer(customerId)).build();
    }

    //Lay token tu header request sau do lay username tu token
    @GetMapping("/profile")
    public ApiResponse<CustomerResponseDTO> getCustomerProfile(@RequestHeader(value = "Authorization",
            required = false) String auth) throws AppException {

        String token = auth.replace("Bearer ", "");
        if (!jwtUtil.verifyToken(token)) {
            throw new AppException(ErrorCode.INVALID_TOKEN);
        }

        String username = jwtUtil.extractUsername(token);
        return ApiResponse.<CustomerResponseDTO>builder().data(customerService.getCustomerProfile(username)).build();
    }

    @GetMapping("/list")
    public ApiResponse<List<CustomerResponseDTO>> getCustomers() {

        return ApiResponse.<List<CustomerResponseDTO>>builder().data(customerService.getAllCustomers()).build();
    }

    @GetMapping("/checkUsername/{username}")
    public ApiResponse<Boolean> checkUsername(@PathVariable String username) {
//        return customerService.checkUsername(username);

        return ApiResponse.<Boolean>builder().data(customerService.checkUsername(username)).build();
    }

    @PostMapping("/login")
    public ApiResponse<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        return ApiResponse.<LoginResponse>builder()
                .code(HttpStatus.OK.value())
                .data(customerService.login(loginRequest))
                .build();
    }

    @PostMapping("/introspect")
    public IntrospectResponse authenticate(@RequestBody IntrospectRequest request) throws Exception {
        return customerService.introspect(request);


    }

    @PostMapping("/resetPassword/{username}")
    public ApiResponse resetPassword(
            @PathVariable String username,
            @RequestParam String resetCode,
            @RequestParam String newPassword) {
        customerService.resetPassword(username, resetCode, newPassword);

        return ApiResponse.builder().code(2000).message("Đổi mật khẩu thành công").build();

    }


    @PostMapping("/initPasswordReset/{username}")
    public ApiResponse initPasswordReset(@PathVariable String username) {
        customerService.initPasswordReset(username);

        return ApiResponse.builder().code(2000).message("Hãy kiểm tra mã xác nhận đã được gửi vào email của bạn!").build();

    }

    @PutMapping("/updateByUser/{id}")
    public ApiResponse<CustomerResponseDTO> updateByUser(@PathVariable int id, @RequestBody CustomerUpdateRequestDTO customer) {
//        return customerService.updateCustomerById(id, customer);

        return ApiResponse.<CustomerResponseDTO>builder().message("Cập nhật thông tin thành công!").data(customerService.updateCustomerById(id, customer)).build();
    }

    @PatchMapping("/changePassword/{customerId}")
    public ApiResponse changePassword(@PathVariable int customerId, @RequestBody ChangePasswordDto dto) throws AppException {
        customerService.changePassword(customerId, dto.getOldPassword(), dto.getNewPassword());

        return ApiResponse.builder().code(2000).message("Đổi mật khẩu thành công").build();

    }
//    @GetMapping("/getCustomerByUsername/{username}")
//    public CustomerResponseDTO getCustomerByUsername(@PathVariable String username) {
//        return customerServiceImpl.getCustomerByUsername(username);
//    }

}


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

    @GetMapping("/quantity/{customerId}")
    public int getQuantity(@PathVariable("customerId") int customerId) {
        return customerService.getQuantityByCustomerId(customerId);
    }

    @PostMapping
    public ApiResponse addCustomer(@RequestBody @Valid CustomerRequestDTO customer) throws AppException {
        if (customerService.checkUsername(customer.getUsername())) {
            throw new AppException(ErrorCode.USERNAME_TAKEN);
        } else if (customerService.checkEmail(customer.getEmail())) {
            throw new AppException(ErrorCode.EMAIL_TAKEN);

        }

        int userId = customerService.saveCustomer(customer);
        CartRequestDTO cartRequestDTO = new CartRequestDTO();
        cartRequestDTO.setCustomerId(userId);
        cartService.saveCart(cartRequestDTO);
        return ApiResponse.builder().code(2000).message("Thêm người dùng thành công").build();
    }

    @PutMapping("/{customerId}")
    public void updateCustomer(@PathVariable int customerId, @RequestBody CustomerRequestDTO customer) {
        customerService.updateCustomer(customerId, customer);
    }

    @PutMapping("/admin/{customerId}")
    public void updateAdmin(@PathVariable int customerId, @RequestBody CustomerRequestDTO customer) {
        customerService.updateByAdmin(customerId, customer);
    }

    @DeleteMapping("/{customerId}")
    public void deleteCustomer(@PathVariable int customerId) {
        customerService.deleteCustomer(customerId);
    }

    @GetMapping("/{customerId}")
    public CustomerResponseDTO getCustomer(@PathVariable int customerId) {
        return customerService.getCustomer(customerId);
    }

    @GetMapping("/list")
    public ApiResponse<List<CustomerResponseDTO>> getCustomers() {

        return ApiResponse.<List<CustomerResponseDTO>>builder().data(customerService.getAllCustomers()).build();
    }

    @GetMapping("/checkUsername/{username}")
    public boolean checkUsername(@PathVariable String username) {
        return customerService.checkUsername(username);
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
    public void resetPassword(
            @PathVariable String username,
            @RequestParam String resetCode,
            @RequestParam String newPassword) {
        customerService.resetPassword(username, resetCode, newPassword);
    }


    @PostMapping("/initPasswordReset/{username}")
    public void initPasswordReset(@PathVariable String username) {
        customerService.initPasswordReset(username);
    }

    @PutMapping("/updateByUser/{id}")
    public CustomerResponseDTO updateByUser(@PathVariable int id, @RequestBody CustomerUpdateRequestDTO customer) {
        return customerService.updateCustomerById(id, customer);
    }

    @PatchMapping("/changePassword/{customerId}")
    public void changePassword(@PathVariable int customerId, @RequestBody ChangePasswordDto dto) {
        customerService.changePassword(customerId, dto.getOldPassword(), dto.getNewPassword());
    }
//    @GetMapping("/getCustomerByUsername/{username}")
//    public CustomerResponseDTO getCustomerByUsername(@PathVariable String username) {
//        return customerServiceImpl.getCustomerByUsername(username);
//    }

}


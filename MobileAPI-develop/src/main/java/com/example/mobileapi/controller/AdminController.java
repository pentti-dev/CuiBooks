package com.example.mobileapi.controller;

import com.example.mobileapi.dto.request.CartRequestDTO;
import com.example.mobileapi.dto.request.CustomerRequestDTO;
import com.example.mobileapi.dto.response.ApiResponse;
import com.example.mobileapi.dto.response.CustomerResponseDTO;
import com.example.mobileapi.exception.AppException;
import com.example.mobileapi.exception.ErrorCode;
import com.example.mobileapi.service.CartService;
import com.example.mobileapi.service.CustomerService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@Tag(name = "Admin", description = "Admin API")
@FieldDefaults(makeFinal = true, level = lombok.AccessLevel.PRIVATE)
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {
    CustomerService customerService;
    CartService cartService;

    @GetMapping("/customers")
    public ApiResponse<List<CustomerResponseDTO>> getCustomers() {

        return ApiResponse.<List<CustomerResponseDTO>>builder().data(customerService.getAllCustomers()).build();
    }

    @GetMapping("/customer/{customerId}")
    public ApiResponse<CustomerResponseDTO> getCustomer(@PathVariable int customerId) {
        return ApiResponse.<CustomerResponseDTO>builder().data(customerService.getCustomer(customerId)).build();
    }

    @PostMapping("/customer")
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

    @PutMapping("/customer/{customerId}")
    public ApiResponse updateCustomer(@PathVariable int customerId, @RequestBody CustomerRequestDTO customer) throws AppException {


        return ApiResponse.builder().code(2000)
                .message("Cập nhật người dùng thành công")
                .data(customerService.updateCustomerById(customerId, customer))
                .build();
    }

    @DeleteMapping("/customer/{customerId}")
    public ApiResponse deleteCustomer(@PathVariable int customerId) {
        customerService.deleteCustomer(customerId);
        return ApiResponse.builder().code(2000).message("Xóa người dùng thành công").build();
    }


}

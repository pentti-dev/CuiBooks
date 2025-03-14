package com.example.mobileapi.controller;

import com.example.mobileapi.dto.request.*;
import com.example.mobileapi.dto.response.ApiResponse;
import com.example.mobileapi.dto.response.CustomerResponseDTO;
import com.example.mobileapi.dto.response.IntrospectResponse;
import com.example.mobileapi.dto.response.LoginResponse;
import com.example.mobileapi.service.CartService;
import com.example.mobileapi.service.CustomerService;
import com.example.mobileapi.service.impl.CustomerServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customer")
@RequiredArgsConstructor
@Tag(name = "Customer", description = "Customer API")
public class CustomerController {
    private static final Logger log = LoggerFactory.getLogger(CustomerController.class);
    private final CustomerService customerService;
    private final CartService cartService;
    private final CustomerServiceImpl customerServiceImpl;

    @GetMapping("/quantity/{customerId}")
    public int getQuantity(@PathVariable("customerId") int customerId) {
        return customerService.getQuantityByCustomerId(customerId);
    }

    @PostMapping
    public int addCustomer(@RequestBody CustomerRequestDTO customer) {
        if (customerService.checkUsername(customer.getUsername())) {
            return -1;
        }
        int userId = customerService.saveCustomer(customer);
        CartRequestDTO cartRequestDTO = new CartRequestDTO();
        cartRequestDTO.setCustomerId(userId);
        cartService.saveCart(cartRequestDTO);
        return userId;
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
    public LoginResponse login(@RequestBody LoginRequest loginRequest) {
        LoginResponse token = customerService.getToken(loginRequest.getUsername(), loginRequest.getPassword());
//        cus.setCartId(cartService.getCartByUsername(loginRequest.getUsername()).getId());
        return token;
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


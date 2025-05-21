package com.example.mobileapi.controller;

import com.example.mobileapi.annotation.GetToken;
import com.example.mobileapi.dto.request.CustomerRequestDTO;
import com.example.mobileapi.dto.request.IntrospectRequest;
import com.example.mobileapi.dto.request.LoginRequest;
import com.example.mobileapi.dto.response.ApiResponse;
import com.example.mobileapi.dto.response.IntrospectResponse;
import com.example.mobileapi.dto.response.LoginResponse;
import com.example.mobileapi.exception.AppException;
import com.example.mobileapi.service.AuthenticationService;
import com.example.mobileapi.service.CartService;
import com.example.mobileapi.service.CustomerService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Authentication API")
@FieldDefaults(makeFinal = true, level = lombok.AccessLevel.PRIVATE)
public class AuthenticationController {

    AuthenticationService authenticationService;
    CustomerService customerService;
    CartService cartService;

    @PostMapping("/login")
    public ApiResponse<LoginResponse> login(@RequestBody LoginRequest loginRequest) throws AppException {
        return ApiResponse.<LoginResponse>builder()
                .code(HttpStatus.OK.value())
                .data(authenticationService.login(loginRequest))
                .build();
    }

    @PostMapping("/introspect")
    public IntrospectResponse authenticate(@RequestBody IntrospectRequest request) throws Exception {
        return authenticationService.introspect(request);


    }

    @PostMapping("/logout")
    public ApiResponse<Void> logout(@Parameter(hidden = true) @GetToken String token) {
        try {
            log.info("Token length: {}", token.length());
            authenticationService.logout(token);
            return ApiResponse.success("Đăng xuất thành công");
        } catch (AppException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/register")
    public ApiResponse<Void> addCustomer(@RequestBody @Valid CustomerRequestDTO customer) throws AppException {
        customerService.saveCustomer(customer);
        return ApiResponse.success("Đăng ký tài khoản thành công");
    }

    @PostMapping("/checkTokenExpiration/{token}")
    public ApiResponse<Void> checkTokenExpiration(@PathVariable("token") String token) throws AppException {
        authenticationService.checkTokenExpiration(token);
        return ApiResponse.success();
    }

}

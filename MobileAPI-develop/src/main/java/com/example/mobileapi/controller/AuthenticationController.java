package com.example.mobileapi.controller;

import com.example.mobileapi.annotation.GetToken;
import com.example.mobileapi.dto.request.CustomerRequestDTO;
import com.example.mobileapi.dto.request.LoginRequest;
import com.example.mobileapi.dto.response.ApiResponse;
import com.example.mobileapi.dto.response.LoginResponse;
import com.example.mobileapi.exception.AppException;
import com.example.mobileapi.exception.ErrorCode;
import com.example.mobileapi.service.AuthenticationService;
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

    @PostMapping("/login")
    public ApiResponse<LoginResponse> login(@RequestBody LoginRequest loginRequest) throws AppException {
        return ApiResponse.<LoginResponse>builder()
                .code(HttpStatus.OK.value())
                .data(authenticationService.login(loginRequest))
                .build();
    }


    @PostMapping("/logout")
    public ApiResponse<Void> logout(@Parameter(hidden = true) @GetToken String token) {
        if (token == null || token.isEmpty()) {
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }
        authenticationService.logout(token);
        return ApiResponse.success();
    }

    @PostMapping("/register")
    public ApiResponse<Void> register(@RequestBody @Valid CustomerRequestDTO customer) throws AppException {

        customerService.saveCustomer(customer);
        return ApiResponse.success();
    }

    @PostMapping("/check-username")
    public ApiResponse<Boolean> checkUsername(@RequestBody String username) {
        return ApiResponse.<Boolean>builder()
                .code(HttpStatus.OK.value())
                .data(customerService.checkUsername(username))
                .build();
    }

    @PostMapping("/check-email")
    public ApiResponse<Boolean> checkEmail(@RequestBody String email) {
        return ApiResponse.<Boolean>builder()
                .code(HttpStatus.OK.value())
                .data(customerService.checkEmail(email))
                .build();
    }   
     @PostMapping("/checkTokenExpiration/{token}")
    public ApiResponse<Void> checkTokenExpiration(@PathVariable("token") String token) throws AppException {
        authenticationService.checkTokenExpiration(token);
        return ApiResponse.success();
    }

}

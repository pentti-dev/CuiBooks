package com.example.mobileapi.service;

import com.example.mobileapi.dto.request.IntrospectRequest;
import com.example.mobileapi.dto.request.LoginRequest;
import com.example.mobileapi.dto.response.IntrospectResponse;
import com.example.mobileapi.dto.response.LoginResponse;
import com.example.mobileapi.exception.AppException;
import com.example.mobileapi.repository.CustomerRepository;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public interface AuthenticationService {

    LoginResponse login(LoginRequest loginRequest) throws AppException;

    void logout(String auth) throws AppException;

    IntrospectResponse introspect(IntrospectRequest request) throws Exception;


}

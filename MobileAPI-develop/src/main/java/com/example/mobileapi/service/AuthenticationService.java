package com.example.mobileapi.service;

import com.example.mobileapi.dto.request.IntrospectRequest;
import com.example.mobileapi.dto.request.LoginRequest;
import com.example.mobileapi.dto.response.IntrospectResponse;
import com.example.mobileapi.dto.response.LoginResponse;
import com.example.mobileapi.exception.AppException;

public interface AuthenticationService {

    LoginResponse login(LoginRequest loginRequest) throws AppException;

    void logout(String token) throws AppException;

    boolean isTokenBlacklisted(String jwtID);

    void checkTokenExpiration(String token) throws AppException;


}

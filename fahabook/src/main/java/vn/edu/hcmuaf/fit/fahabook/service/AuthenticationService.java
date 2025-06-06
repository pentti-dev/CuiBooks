package vn.edu.hcmuaf.fit.fahabook.service;

import vn.edu.hcmuaf.fit.fahabook.dto.request.LoginRequest;
import vn.edu.hcmuaf.fit.fahabook.dto.response.LoginResponse;
import vn.edu.hcmuaf.fit.fahabook.exception.AppException;

public interface AuthenticationService {

    LoginResponse login(LoginRequest loginRequest) throws AppException;

    void logout(String token) throws AppException;

    boolean isTokenBlacklisted(String jwtID);

    void checkTokenExpiration(String token) throws AppException;
}

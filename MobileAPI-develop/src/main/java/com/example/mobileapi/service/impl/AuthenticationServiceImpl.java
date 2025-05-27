package com.example.mobileapi.service.impl;

import com.example.mobileapi.config.BCryptPasswordEncoder;
import com.example.mobileapi.dto.request.IntrospectRequest;
import com.example.mobileapi.dto.request.LoginRequest;
import com.example.mobileapi.dto.response.IntrospectResponse;
import com.example.mobileapi.dto.response.LoginResponse;
import com.example.mobileapi.exception.AppException;
import com.example.mobileapi.exception.ErrorCode;
import com.example.mobileapi.entity.Customer;
import com.example.mobileapi.entity.InvalidateToken;
import com.example.mobileapi.repository.CustomerRepository;
import com.example.mobileapi.repository.InvalidateTokenRepository;
import com.example.mobileapi.service.AuthenticationService;
import com.example.mobileapi.util.JwtUtil;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.Date;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = lombok.AccessLevel.PRIVATE)
public class AuthenticationServiceImpl implements AuthenticationService {
    CustomerRepository customerRepository;
    InvalidateTokenRepository invalidateTokenRepository;
    BCryptPasswordEncoder passwordEncoder;

    @Override
    public LoginResponse login(LoginRequest loginRequest) throws AppException {
        log.warn("Username: " + loginRequest.getUsername() + " Password: " + loginRequest.getPassword());
        Customer customer = customerRepository.findByUsername(loginRequest.getUsername())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        if (passwordEncoder.matches(loginRequest.getPassword(), customer.getPassword())) { // Sử dụng passwordEncoder

            var token = JwtUtil.generateToken(customer);
            return LoginResponse.builder()
                    .token(token)
                    .build();
        } else {
            throw new AppException(ErrorCode.WRONG_PASSWORD);
        }
    }

    @Override
    public void logout(String token) throws AppException {
        if (token == null || token.isEmpty()) {
            throw new AppException(ErrorCode.INVALID_TOKEN);
        }
        String jwtID = JwtUtil.getJwtIDFromToken(token);
        invalidateTokenRepository.save(InvalidateToken.builder()
                .id(jwtID)
                .expiryTime(JwtUtil.getExpirationTimeFromToken(token))
                .build());

    }

    @Override
    public void checkTokenExpiration(String token) throws AppException {
        try {
            // Giải mã token và kiểm tra thời gian hết hạn
            SignedJWT jwt = SignedJWT.parse(token);
            JWTClaimsSet claims = jwt.getJWTClaimsSet();
            Date expirationTime = claims.getExpirationTime();

            // Kiểm tra nếu token đã hết hạn
            if (expirationTime.before(new Date())) {
                throw new AppException(ErrorCode.TOKEN_EXPIRED);
            }
        } catch (ParseException e) {
            log.error("Error parsing token", e);
            throw new AppException(ErrorCode.INVALID_TOKEN);
        }
    }

    @Override
    public boolean isTokenBlacklisted(String jwtID) {
        return invalidateTokenRepository.existsById(jwtID);
    }

}

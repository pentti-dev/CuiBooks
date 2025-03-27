package com.example.mobileapi.service.impl;

import com.example.mobileapi.config.BCryptPasswordEncoder;
import com.example.mobileapi.dto.request.IntrospectRequest;
import com.example.mobileapi.dto.request.LoginRequest;
import com.example.mobileapi.dto.response.IntrospectResponse;
import com.example.mobileapi.dto.response.LoginResponse;
import com.example.mobileapi.exception.AppException;
import com.example.mobileapi.exception.ErrorCode;
import com.example.mobileapi.model.Customer;
import com.example.mobileapi.model.InvalidateToken;
import com.example.mobileapi.repository.CustomerRepository;
import com.example.mobileapi.repository.InvalidateTokenRepository;
import com.example.mobileapi.service.AuthenticationService;
import com.example.mobileapi.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = lombok.AccessLevel.PRIVATE)
public class AuthenticationServiceImpl implements AuthenticationService {
    CustomerRepository customerRepository;
    InvalidateTokenRepository invalidateTokenRepository;
    BCryptPasswordEncoder passwordEncoder;
    JwtUtil jwtUtil;

    @Override
    public LoginResponse login(LoginRequest loginRequest) throws AppException {
        log.warn("Username: " + loginRequest.getUsername() + " Password: " + loginRequest.getPassword());
        Customer customer = customerRepository.findByUsername(loginRequest.getUsername())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        if (passwordEncoder.matches(loginRequest.getPassword(), customer.getPassword())) { // Sử dụng passwordEncoder

            var token = jwtUtil.generateToken(customer);
            return LoginResponse.builder()
                    .token(token)
                    .build();
        } else {
            throw new AppException(ErrorCode.WRONG_PASSWORD);
        }
    }

    @Override
    public void logout(String token) {
        String jwtID = jwtUtil.getJwtIDFromToken(token);
        invalidateTokenRepository.save(InvalidateToken.builder()
                .id(jwtID)
                .expiryTime(jwtUtil.getExpirationTimeFromToken(token))
                .build());

    }

    @Override
    public IntrospectResponse introspect(IntrospectRequest request) {
//        try {
//            JWSVerifier verifier = new RSASSAVerifier(jwtUtil.loadPublicKey());
//
//            var token = request.getToken();
//            SignedJWT signedJWT = SignedJWT.parse(token);
//
//            Date expirationTime = signedJWT.getJWTClaimsSet().getExpirationTime();
//
//            var verify = signedJWT.verify(verifier);
//
//            return IntrospectResponse.builder()
//                    .valid(verify && expirationTime.after(new Date()))
//                    .build();
//        } catch (JOSEException e) {
//            throw new RuntimeException(e);
//        } catch (ParseException e) {
//            throw new RuntimeException(e);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
        return null;
    }
}

package vn.edu.hcmuaf.fit.fahabook.service.impl;

import java.text.ParseException;
import java.util.Date;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import vn.edu.hcmuaf.fit.fahabook.config.BCryptPasswordEncoder;
import vn.edu.hcmuaf.fit.fahabook.dto.request.LoginRequest;
import vn.edu.hcmuaf.fit.fahabook.dto.response.LoginResponse;
import vn.edu.hcmuaf.fit.fahabook.entity.Customer;
import vn.edu.hcmuaf.fit.fahabook.entity.InvalidateToken;
import vn.edu.hcmuaf.fit.fahabook.entity.enums.CustomerStatus;
import vn.edu.hcmuaf.fit.fahabook.exception.AppException;
import vn.edu.hcmuaf.fit.fahabook.exception.ErrorCode;
import vn.edu.hcmuaf.fit.fahabook.repository.CustomerRepository;
import vn.edu.hcmuaf.fit.fahabook.repository.InvalidateTokenRepository;
import vn.edu.hcmuaf.fit.fahabook.service.AuthenticationService;
import vn.edu.hcmuaf.fit.fahabook.service.EmailService;
import vn.edu.hcmuaf.fit.fahabook.util.JwtUtil;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = lombok.AccessLevel.PRIVATE)
public class AuthenticationServiceImpl implements AuthenticationService {
    CustomerRepository customerRepository;
    InvalidateTokenRepository invalidateTokenRepository;
    BCryptPasswordEncoder passwordEncoder;
    EmailService emailService;

    @Override
    public LoginResponse login(LoginRequest loginRequest) throws AppException {
        log.info("Username: {} ", loginRequest.getUsername());

        Customer customer = customerRepository
                .findByUsername(loginRequest.getUsername())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        validateCustomerLogin(customer);


        if (passwordEncoder.matches(loginRequest.getPassword(), customer.getPassword())) {

            var token = JwtUtil.generateToken(customer);
            return new LoginResponse(token);
        } else {
            throw new AppException(ErrorCode.WRONG_PASSWORD);
        }
    }

    private void validateCustomerLogin(Customer customer) {
        if (customer.getStatus().equals(CustomerStatus.DELETED)) {
            throw new AppException(ErrorCode.ACCOUNT_DELETED);
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
            SignedJWT jwt = SignedJWT.parse(token);
            JWTClaimsSet claims = jwt.getJWTClaimsSet();
            Date expirationTime = claims.getExpirationTime();

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

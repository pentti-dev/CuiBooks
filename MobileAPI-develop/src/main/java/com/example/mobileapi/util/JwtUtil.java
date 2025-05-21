package com.example.mobileapi.util;

import com.example.mobileapi.config.props.JwtProperties;
import com.example.mobileapi.entity.Customer;
import com.example.mobileapi.entity.enums.Role;
import com.example.mobileapi.repository.InvalidateTokenRepository;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;

@Slf4j
public final class JwtUtil {

    private static RSAPrivateKey privateKey;
    private static RSAPublicKey publicKey;
    private static JwtProperties jwtProps;
    private static InvalidateTokenRepository blacklistRepo;

    private JwtUtil() {
        throw new UnsupportedOperationException("Utility class should not be instantiated");
    }

    // Gọi hàm này một lần tại thời điểm khởi tạo bean trong config
    public static void init(JwtProperties props, InvalidateTokenRepository blacklist) {
        jwtProps = props;
        blacklistRepo = blacklist;
        Resource privateKeyResource = props.keys().privateKey();
        Resource publicKeyResource = props.keys().publicKey();

        privateKey = KeyUtil.parsePrivateKey(privateKeyResource);
        publicKey = KeyUtil.parsePublicKey(publicKeyResource);
    }

    public static String generateToken(Customer user) {
        Instant now = Instant.now();

        try {
            JWTClaimsSet claims = new JWTClaimsSet.Builder()
                    .issuer(jwtProps.issuer())
                    .subject(user.getUsername())
                    .issueTime(Date.from(now))
                    .expirationTime(Date.from(now.plus(jwtProps.expirationMinutes(), ChronoUnit.MINUTES)))
                    .jwtID(UUID.randomUUID().toString())
                    .claim("scope", Role.role(user.isRole()))
                    .claim("id", user.getId())
                    .build();

            JWSHeader header = new JWSHeader(JWSAlgorithm.RS512);
            SignedJWT jwt = new SignedJWT(header, claims);
            jwt.sign(new RSASSASigner(privateKey));

            return jwt.serialize();
        } catch (JOSEException e) {
            log.error("Error while generating JWT token", e);
            throw new RuntimeException("JWT creation failed", e);
        }
    }

    public static boolean verifyToken(String token) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(token);
            Date expirationTime = signedJWT.getJWTClaimsSet().getExpirationTime();

            boolean isValid = signedJWT.verify(new RSASSAVerifier(publicKey));
            if (!isValid) {
                log.warn("Invalid token");
            } else if (expirationTime.before(new Date())) {
                log.warn("Expired token");
                return false;
            }
            return isValid;
        } catch (Exception e) {
            log.warn("Error verifying token", e);
            return false;
        }
    }

    public static String getUserNameFromToken(String token) {
        try {
            return SignedJWT.parse(token).getJWTClaimsSet().getSubject();
        } catch (Exception e) {
            log.warn("Error extracting username from token", e);
            return null;
        }
    }

    public static String getJwtIDFromToken(String token) {
        try {
            return SignedJWT.parse(token).getJWTClaimsSet().getJWTID();
        } catch (Exception e) {
            log.warn("Error extracting jwtID from token", e);
            return null;
        }
    }

    public static Date getExpirationTimeFromToken(String token) {
        try {
            return SignedJWT.parse(token).getJWTClaimsSet().getExpirationTime();
        } catch (Exception e) {
            log.warn("Error extracting expiration time from token", e);
            return null;
        }
    }

    public static boolean isLogout(String token) {
        try {
            String jwtID = SignedJWT.parse(token).getJWTClaimsSet().getJWTID();
            return blacklistRepo.existsById(jwtID);
        } catch (Exception e) {
            log.warn("Error checking token logout", e);
            return false;
        }
    }

    public static String resolveToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7);
        }
        return null;
    }
}

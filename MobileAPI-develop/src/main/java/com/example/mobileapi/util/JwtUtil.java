package com.example.mobileapi.util;

import com.example.mobileapi.entity.Customer;
import com.example.mobileapi.entity.enums.Role;
import com.example.mobileapi.repository.InvalidateTokenRepository;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import jakarta.annotation.PostConstruct;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
@Component
@RequiredArgsConstructor
public class JwtUtil {

    static final String PRIVATE_KEY_PATH = "src/main/resources/keys/private_key.pem";
    static final String PUBLIC_KEY_PATH = "src/main/resources/keys/public_key.pem";
    final InvalidateTokenRepository invalidateTokenRepository;

    RSAPrivateKey privateKey;
    RSAPublicKey publicKey;

    @PostConstruct
    public void init() {
        this.privateKey = loadPrivateKey();
        this.publicKey = loadPublicKey();
    }

    public RSAPrivateKey loadPrivateKey() {
        return loadKey(PRIVATE_KEY_PATH, true);
    }

    public RSAPublicKey loadPublicKey() {
        return loadKey(PUBLIC_KEY_PATH, false);
    }

    private <T> T loadKey(String keyPath, boolean isPrivateKey) {
        try {
            String key = Files.readString(Paths.get(keyPath))
                    .replace("-----BEGIN " + (isPrivateKey ? "PRIVATE" : "PUBLIC") + " KEY-----", "")
                    .replace("-----END " + (isPrivateKey ? "PRIVATE" : "PUBLIC") + " KEY-----", "")
                    .replaceAll("\\s", "");

            byte[] decodedKey = Base64.getDecoder().decode(key);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");

            if (isPrivateKey) {
                PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(decodedKey);
                return (T) keyFactory.generatePrivate(spec);
            } else {
                X509EncodedKeySpec spec = new X509EncodedKeySpec(decodedKey);
                return (T) keyFactory.generatePublic(spec);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error loading " + (isPrivateKey ? "private" : "public") + " key", e);
        }
    }

    // 🛠 Tạo JWT token
    public String generateToken(Customer customer) {
        try {
            JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                    .subject(customer.getUsername())
                    .issuer("ngoctaiphan")
                    .issueTime(new Date())
                    .expirationTime(Date.from(Instant.now().plus(30, ChronoUnit.MINUTES)))
                    .jwtID(UUID.randomUUID().toString())
                    .claim("scope", Role.role(customer.isRole()))
                    .build();

            SignedJWT signedJWT = new SignedJWT(
                    new JWSHeader(JWSAlgorithm.RS512),
                    jwtClaimsSet
            );

            signedJWT.sign(new RSASSASigner(privateKey));
            return signedJWT.serialize();
        } catch (JOSEException e) {
            log.warn("Error signing token", e);
            throw new RuntimeException("Error generating JWT token", e);
        }
    }

    // 🛠 Xác thực JWT token
    public boolean verifyToken(String token) {
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

    public String getUserNameFormToken(String token) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(token);
            return signedJWT.getJWTClaimsSet().getSubject();
        } catch (Exception e) {
            log.warn("Error extracting username from token", e);
            return null;
        }
    }

    public String getJwtIDFromToken(String token) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(token);
            return signedJWT.getJWTClaimsSet().getJWTID();
        } catch (Exception e) {
            log.warn("Error extracting jwtID from token", e);
            return null;
        }
    }

    public Date getExpirationTimeFromToken(String token) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(token);
            log.error("Expiration time: {}", signedJWT.getJWTClaimsSet().getExpirationTime());
            return signedJWT.getJWTClaimsSet().getExpirationTime();
        } catch (Exception e) {
            log.warn("Error extracting expiration time from token", e);
            return null;
        }
    }

    public boolean isLogout(String token) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(token);
            String jwtID = signedJWT.getJWTClaimsSet().getJWTID();
            return invalidateTokenRepository.existsById(jwtID);
        } catch (Exception e) {
            log.warn("Error extracting expiration time from token", e);
            return false;
        }
    }

}

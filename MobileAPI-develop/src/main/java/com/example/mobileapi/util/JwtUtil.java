package com.example.mobileapi.util;


import com.example.mobileapi.model.Customer;
import com.example.mobileapi.model.enums.Role;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.StringJoiner;

@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
@Component
@NoArgsConstructor
public class JwtUtil {

    String privateKeyPath = "src/main/resources/keys/private_key.pem";
    String publicKeyPath = "src/main/resources/keys/public_key.pem";



    RSAPrivateKey loadPrivateKey() {
        try {
            String key = Files.readString(Paths.get(privateKeyPath))
                    .replace("-----BEGIN PRIVATE KEY-----", "")
                    .replace("-----END PRIVATE KEY-----", "")
                    .replaceAll("\\s", "");
            byte[] decodedKey = java.util.Base64.getDecoder().decode(key);
            PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(decodedKey);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return (RSAPrivateKey) keyFactory.generatePrivate(spec);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
    }

    public RSAPublicKey loadPublicKey() {
        try {
            String key = Files.readString(Paths.get(publicKeyPath))
                    .replace("-----BEGIN PUBLIC KEY-----", "")
                    .replace("-----END PUBLIC KEY-----", "")
                    .replaceAll("\\s", "");

            byte[] decodedKey = java.util.Base64.getDecoder().decode(key);
            X509EncodedKeySpec spec = new X509EncodedKeySpec(decodedKey);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            log.warn("Load public key to verify token");
            return (RSAPublicKey) keyFactory.generatePublic(spec);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
    }
// generate token

    public String generateToken(Customer customer) {
        JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.RS512);
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(customer.getUsername())
                .issuer("ngoctaiphan")//người tạo token
                .issueTime(new Date())//thời gian tạo token
                .expirationTime(new Date(Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli())) //thời gian hết hạn
                .claim("scope", Role.role(customer.isRole()))
                .build();
        Payload payload = new Payload(jwtClaimsSet.toJSONObject());

        JWSObject jwsObject = new JWSObject(jwsHeader, payload);
        try {
            RSAPrivateKey privateKey = loadPrivateKey();
            //Dùng private key để ký token
            jwsObject.sign(new RSASSASigner(privateKey));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            log.warn("Error signing token", e);
            throw new RuntimeException(e);
        } catch (Exception e) {
            log.warn("Error loading private key", e);
            throw new RuntimeException(e);
        }

    }

    //verify token
    public boolean verifyToken(String token) {
        try {
            RSAPublicKey publicKey = loadPublicKey();
            log.info("Public key: {}", publicKey.toString().length());
            //Dung public key de verify token
            JWSVerifier verifier = new RSASSAVerifier(publicKey);


            SignedJWT signedJWT = SignedJWT.parse(token);
            Date expirationTime = signedJWT.getJWTClaimsSet().getExpirationTime();
            var verify = signedJWT.verify(verifier);
            if (!verify) {
                log.warn("Token is invalid");
            } else if (expirationTime.before(new Date())) {
                log.warn("Token is expired");

            }
            return verify && expirationTime.after(new Date());
        } catch (JOSEException e) {
            log.warn("Error verifying token", e);
            return false;
        } catch (Exception e) {
            log.warn("Error loading public key", e);
            return false;
        }
    }

}

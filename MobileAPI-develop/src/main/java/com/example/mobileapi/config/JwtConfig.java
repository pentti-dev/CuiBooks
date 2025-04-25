package com.example.mobileapi.config;

import com.example.mobileapi.config.props.JwtProperties;
import com.example.mobileapi.repository.InvalidateTokenRepository;
import com.example.mobileapi.util.JwtUtil;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = lombok.AccessLevel.PRIVATE)
public class JwtConfig {

    JwtProperties jwtProperties;
    InvalidateTokenRepository invalidateTokenRepository;

    @PostConstruct
    public void initJwtUtil() {
        JwtUtil.init(jwtProperties, invalidateTokenRepository);
    }
}

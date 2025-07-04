package vn.edu.hcmuaf.fit.fahabook.config;

import jakarta.annotation.PostConstruct;

import org.springframework.context.annotation.Configuration;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import vn.edu.hcmuaf.fit.fahabook.config.props.JwtProperties;
import vn.edu.hcmuaf.fit.fahabook.repository.InvalidateTokenRepository;
import vn.edu.hcmuaf.fit.fahabook.util.JwtUtil;

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

package com.example.mobileapi.config.props;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.io.Resource;
import org.springframework.validation.annotation.Validated;

@ConfigurationProperties(prefix = "jwt")
@Validated
public record JwtProperties(
                @NotBlank String issuer,
                @NotBlank String audience,
                @Min(1) long expirationMinutes,
                @NotNull ResourceConfig keys) {
        public record ResourceConfig(
                        @NotNull Resource privateKey,
                        @NotNull Resource publicKey) {
        }
}

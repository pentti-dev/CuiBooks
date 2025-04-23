package com.example.mobileapi.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import jakarta.validation.constraints.NotBlank;

@ConfigurationProperties(prefix = "vnpay")
public record VnPayProperties(
        @NotBlank String tmnCode,
        @NotBlank String hashSecret,
        @NotBlank String payUrl,
        @NotBlank String returnUrl,
        @NotBlank String version,
        @NotBlank String command,
        @NotBlank String currencyCode,
        @NotBlank String locale,
        @NotBlank String bankCode,
        @NotBlank String ipAddr

) {
}

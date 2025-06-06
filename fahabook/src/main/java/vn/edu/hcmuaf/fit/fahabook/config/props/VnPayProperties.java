package vn.edu.hcmuaf.fit.fahabook.config.props;

import jakarta.validation.constraints.NotBlank;

import org.springframework.boot.context.properties.ConfigurationProperties;

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
        @NotBlank String ipAddr) {}

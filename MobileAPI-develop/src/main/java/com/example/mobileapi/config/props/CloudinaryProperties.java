package com.example.mobileapi.config.props;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import static lombok.AccessLevel.PRIVATE;

@Data
@FieldDefaults(level = PRIVATE)
@Component
@ConfigurationProperties(prefix = "cloudinary")
@Validated
public class CloudinaryProperties {
    @NotBlank
    String cloudName;
    @NotBlank
    String apiKey;
    @NotBlank
    String apiSecret;
}

package vn.edu.hcmuaf.fit.fahabook.config.props;

import static lombok.AccessLevel.PRIVATE;

import jakarta.validation.constraints.NotBlank;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import lombok.Data;
import lombok.experimental.FieldDefaults;

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

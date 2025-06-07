package vn.edu.hcmuaf.fit.fahabook.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cloudinary.Cloudinary;

import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import vn.edu.hcmuaf.fit.fahabook.config.props.CloudinaryProperties;

@Configuration
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
@AllArgsConstructor
public class CloudinaryConfig {
    CloudinaryProperties properties;

    @Bean
    public Cloudinary cloudinary() {
        Map<String, String> config = new HashMap<>();
        config.put("cloud_name", properties.getCloudName());
        config.put("api_key", properties.getApiKey());
        config.put("api_secret", properties.getApiSecret());
        return new Cloudinary(config);
    }
}

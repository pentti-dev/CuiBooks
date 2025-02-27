package com.example.mobileapi.config;

import com.cloudinary.Cloudinary;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class CloudinaryConfig {
    @Bean
    public Cloudinary cloudinary() {
        Map<String, String> config = new HashMap<>();
        config.put("cloud_name", "drvydie5q");
        config.put("api_key", "241959425679189");
        config.put("api_secret", "efKXKXZgID6nX_5tPItvYZn35rY");
        return new Cloudinary(config);
    }
}


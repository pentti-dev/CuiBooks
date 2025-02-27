package com.example.mobileapi.staticconfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Cấu hình đường dẫn cho các file tĩnh
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");
    }
}

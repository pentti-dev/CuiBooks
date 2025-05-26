package com.example.mobileapi.config;


import com.example.mobileapi.entity.Customer;
import com.example.mobileapi.entity.enums.Role;
import com.example.mobileapi.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
@FieldDefaults(makeFinal = true, level = lombok.AccessLevel.PRIVATE)
@Slf4j
public class ApplicationInitConfig {
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Bean
    ApplicationRunner applicationRunner(CustomerRepository customerRepository) {
        return args -> {
            if (customerRepository.findByUsername("admin").isEmpty()) {
                Customer customer = Customer.builder()
                        .fullname("Admin")
                        .username("admin")
                        .role(Role.ADMIN)
                        .password(bCryptPasswordEncoder.encode("admin"))
                        .email("admin@gmail.com")
                        .phone("0123456789")
                        .build();
                customerRepository.save(customer);
                log.info("Admin account created");
            }


        };
    }
}

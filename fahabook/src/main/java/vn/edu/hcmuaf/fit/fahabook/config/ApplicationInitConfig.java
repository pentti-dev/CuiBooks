package vn.edu.hcmuaf.fit.fahabook.config;

import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import vn.edu.hcmuaf.fit.fahabook.entity.Customer;
import vn.edu.hcmuaf.fit.fahabook.entity.enums.Role;
import vn.edu.hcmuaf.fit.fahabook.repository.CustomerRepository;

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

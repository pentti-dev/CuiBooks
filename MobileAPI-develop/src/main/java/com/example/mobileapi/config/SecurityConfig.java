package com.example.mobileapi.config;

import com.example.mobileapi.util.JwtUtil;
import com.nimbusds.jose.JWSAlgorithm;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import com.nimbusds.jose.proc.SecurityContext;
import com.nimbusds.jwt.proc.ConfigurableJWTProcessor;
import com.nimbusds.jwt.proc.DefaultJWTProcessor;

import java.security.interfaces.RSAPublicKey;
import java.util.List;

@Slf4j
@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
@EnableMethodSecurity
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class SecurityConfig {
    JwtUtil jwtUtil;

    final String[] ACCEPTED_ENDPOINT = {
            "/api/v1/auth/**", "/v2/api-docs", "/v3/api-docs", "/v3/api-docs/**",
            "/swagger-resources", "/swagger-resources/**",
            "/configuration/ui", "/configuration/security",
            "/swagger-ui/**", "/webjars/**", "/swagger-ui.html",
            "/api/customer/login", "/api/customer/introspect", "/api/test/**",
            "/authenticate"};

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeHttpRequests(
                        request ->
                                request.requestMatchers(ACCEPTED_ENDPOINT)
                                        .permitAll()
                                        .anyRequest()
                                        .authenticated());
        httpSecurity.oauth2ResourceServer(
                oauth2 ->
                        oauth2.jwt(
                                jwtConfigurer -> {
                                    jwtConfigurer.decoder(jwtDecoder())
                                            .jwtAuthenticationConverter(jwtAuthenticationConverter());
                                }));
        httpSecurity.csrf(AbstractHttpConfigurer::disable);
        return httpSecurity.build();
    }

    @Bean
    JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter jGA = new JwtGrantedAuthoritiesConverter();
        jGA.setAuthorityPrefix("ROLE_");
        JwtAuthenticationConverter jAC = new JwtAuthenticationConverter();
        jAC.setJwtGrantedAuthoritiesConverter(jGA);


        return jAC;
    }

    //decode token de kiem tra hop le hay khong
    @Bean
    public JwtDecoder jwtDecoder() {
        RSAPublicKey publicKey = jwtUtil.loadPublicKey();

        // Cấu hình JWT Processor với thuật toán RS512
        ConfigurableJWTProcessor<SecurityContext> jwtProcessor = new DefaultJWTProcessor<>();
        jwtProcessor.setJWSKeySelector(
                (header, context) -> {
                    if (!JWSAlgorithm.RS512.equals(header.getAlgorithm())) {
                        throw new RuntimeException("Invalid algorithm: " + header.getAlgorithm());
                    }
                    return List.of(publicKey);
                }
        );

        return new NimbusJwtDecoder(jwtProcessor);
    }


}

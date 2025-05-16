package com.example.mobileapi.config;

import com.example.mobileapi.config.props.JwtProperties;
import com.example.mobileapi.util.JwtUtil;
import com.example.mobileapi.util.KeyUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.security.interfaces.RSAPublicKey;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
@EnableMethodSecurity
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class SecurityConfig {
    JwtProperties props;
    SecurityExceptionHandler exceptionHandler;

    String[] acceptedEndpoint = {
            "/api/v1/auth/**", "/v2/api-docs", "/v3/api-docs", "/v3/api-docs/**",
            "/swagger-resources", "/swagger-resources/**",
            "/configuration/ui", "/configuration/security",
            "/swagger-ui/**", "/webjars/**", "/swagger-ui.html",
            "/api/customer/register", "/api/customer/verifyEmail",
            "/api/customer/initPasswordReset/**", "/api/customer/resetPassword/**",
            "/api/auth/**", "/api/customer/introspect", "/api/test/**", "/api/customer/checkUsername/**", "/api/customer/checkEmail/**"

            , "/graphiql", "/graphql", "/api/graphql/product"
            , "/graphql-ui", "/webjars/**"
    };

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {


        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtExceptionFilter(), UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(exceptionHandler)
                        .accessDeniedHandler(exceptionHandler)
                )
                .authorizeHttpRequests(request ->
                        request.requestMatchers(acceptedEndpoint)
                                .permitAll()
                                .anyRequest().authenticated()
                )
                .oauth2ResourceServer(oauth2 ->
                        oauth2.jwt(jwtConfigurer ->
                                jwtConfigurer.decoder(jwtDecoder())
                                        .jwtAuthenticationConverter(jwtAuthenticationConverter())
                        )
                );


        return httpSecurity.build();
    }


    @Bean
    CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOrigin("http://localhost:3000");
        config.addAllowedMethod("*");
        config.addAllowedHeader("*");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

    @Bean
    JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter jGA = new JwtGrantedAuthoritiesConverter();
        jGA.setAuthorityPrefix("ROLE_");
        JwtAuthenticationConverter jAC = new JwtAuthenticationConverter();
        jAC.setJwtGrantedAuthoritiesConverter(jGA);
        return jAC;
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        RSAPublicKey publicKey = KeyUtil.parsePublicKey(props.keys().publicKey());
        return NimbusJwtDecoder
                .withPublicKey(publicKey)
                .signatureAlgorithm(SignatureAlgorithm.RS512)
                .build();
    }

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public OncePerRequestFilter jwtExceptionFilter() {


        return new OncePerRequestFilter() {
            private static final String ATTR = "jwtError";

            @Override
            protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
                String token = JwtUtil.resolveToken(request);
                if (token != null) {
                    try {
                        jwtDecoder().decode(token);
                        if (JwtUtil.isLogout(token)) request.setAttribute(ATTR, "LOGOUT");


                    } catch (JwtException e) {
                        String msg = e.getMessage().toLowerCase();
                        if (msg.contains("expired")) request.setAttribute(ATTR, "EXPIRED");
                        else if
                        (msg.contains("signature")) request.setAttribute(ATTR, "INVALID_SIGNATURE");
                        else if
                        (msg.contains("unsupported")) request.setAttribute(ATTR, "UNSUPPORTED");
                        else
                            request.setAttribute(ATTR, "INVALID");
                        throw new BadCredentialsException("JWT invalid");


                    }
                }
                filterChain.doFilter(request, response);
            }
        };
    }

}

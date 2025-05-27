// package com.example.mobileapi.config;
package com.example.mobileapi.config;

import com.example.mobileapi.config.props.JwtProperties;
import com.example.mobileapi.entity.Customer;
import com.example.mobileapi.entity.enums.Role;
import com.example.mobileapi.repository.CustomerRepository;
import com.example.mobileapi.service.AuthenticationService;
import com.example.mobileapi.util.KeyUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.security.oauth2.server.resource.authentication.*;
import org.springframework.security.web.SecurityFilterChain;

import java.security.interfaces.RSAPublicKey;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtProperties props;
    private final AuthenticationService logoutTokenService;
    private final SecurityExceptionHandler exceptionHandler;
    private final CustomerRepository customerRepository;

    String[] acceptedEndpoint = {
            "/api/v1/auth/**", "/v2/api-docs", "/v3/api-docs", "/v3/api-docs/**",
            "/swagger-resources", "/swagger-resources/**",
            "/configuration/ui", "/configuration/security",
            "/swagger-ui/**", "/webjars/**", "/swagger-ui.html",
            "/api/customer/register", "/api/customer/verifyEmail",
            "/api/customer/initPasswordReset/**", "/api/customer/resetPassword/**",
            "/api/auth/**", "/api/customer/introspect", "/api/test/**", "/api/customer/checkUsername/**", "/api/customer/checkEmail/**",
            "/api/category/**", "/api/product/**",
            "/api/payments/return",
            "/graphiql", "/graphql", "/api/graphql/product"
            , "/graphql-ui", "/webjars/**"
    };

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .oauth2ResourceServer(
                        oauth2 -> oauth2
                                .authenticationEntryPoint((exceptionHandler))
                                .accessDeniedHandler(exceptionHandler))
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(acceptedEndpoint).permitAll()
                        .anyRequest().authenticated()
                )
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint(exceptionHandler)
                        .accessDeniedHandler(exceptionHandler)
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt
                                .decoder(jwtDecoder())
                                .jwtAuthenticationConverter(jwtAuthenticationConverter())
                        )
                )
                .oauth2Login(oauth2 -> oauth2
//                        .loginPage("oauth2/authorization/google")
                        .userInfoEndpoint(userInfor ->
                                userInfor
                                        .userService(oauth2UserService())))
        ;
        return http.build();
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter gaConverter = new JwtGrantedAuthoritiesConverter();
        gaConverter.setAuthorityPrefix("ROLE_");
        JwtAuthenticationConverter conv = new JwtAuthenticationConverter();
        conv.setJwtGrantedAuthoritiesConverter(gaConverter);
        return conv;
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        RSAPublicKey pubKey = KeyUtil.parsePublicKey(props.keys().publicKey());

        NimbusJwtDecoder decoder = NimbusJwtDecoder
                .withPublicKey(pubKey)
                .signatureAlgorithm(SignatureAlgorithm.RS512)
                .build();

        OAuth2TokenValidator<Jwt> defaultValidator =
                JwtValidators.createDefaultWithIssuer(props.issuer());

        OAuth2TokenValidator<Jwt> blacklistValidator =
                new JwtBlacklistValidator(logoutTokenService);

        DelegatingOAuth2TokenValidator<Jwt> validator =
                new DelegatingOAuth2TokenValidator<>(defaultValidator, blacklistValidator);

        decoder.setJwtValidator(validator);
        return decoder;
    }

    @Bean
    public OAuth2UserService<OAuth2UserRequest, OAuth2User> oauth2UserService() {
        DefaultOAuth2UserService delegate = new DefaultOAuth2UserService();
        return request -> {
            OAuth2User oauth2User = delegate.loadUser(request);
            Map<String, Object> attrs = oauth2User.getAttributes();

            String googleId = (String) attrs.get("sub");
            String email = (String) attrs.get("email");
            String name = (String) attrs.get("name");

            Customer customer = customerRepository.findByEmail(email)
                    .orElseGet(() -> {
                        Customer newCustomer = Customer.builder()
                                .id(UUID.randomUUID())
                                .email(email)
                                .id(UUID.fromString(googleId))
                                .fullname(name)
                                .role(Role.USER)
                                .build();
                        return customerRepository.save(newCustomer);
                    });

            // Tạo GrantedAuthority từ role lưu trong DB
            List<SimpleGrantedAuthority> authorities = List.of(
                    new SimpleGrantedAuthority("ROLE_" + customer.getRole())
            );

            return new DefaultOAuth2User(
                    authorities,
                    attrs,
                    "sub"
            );
        };
    }


}

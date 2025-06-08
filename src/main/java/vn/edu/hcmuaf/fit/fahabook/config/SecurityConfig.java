// package vn.edu.hcmuaf.fit.fahabook.config;
package vn.edu.hcmuaf.fit.fahabook.config;

import java.security.interfaces.RSAPublicKey;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
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

import lombok.RequiredArgsConstructor;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import vn.edu.hcmuaf.fit.fahabook.config.props.JwtProperties;
import vn.edu.hcmuaf.fit.fahabook.entity.Customer;
import vn.edu.hcmuaf.fit.fahabook.entity.enums.Role;
import vn.edu.hcmuaf.fit.fahabook.repository.CustomerRepository;
import vn.edu.hcmuaf.fit.fahabook.service.AuthenticationService;
import vn.edu.hcmuaf.fit.fahabook.service.CustomerService;
import vn.edu.hcmuaf.fit.fahabook.util.JwtUtil;
import vn.edu.hcmuaf.fit.fahabook.util.KeyUtil;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtProperties props;
    private final AuthenticationService logoutTokenService;
    private final SecurityExceptionHandler exceptionHandler;
    private final CustomerService customerService;
    String[] acceptedEndpoint = {
            "/api/v1/auth/**",
            "/v2/api-docs",
            "/v3/api-docs",
            "/v3/api-docs/**",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui/**",
            "/webjars/**",
            "/swagger-ui.html",
            "/api/customer/initPasswordReset/**",
            "/api/customer/resetPassword/**",
            "/api/auth/**",
            "/api/customer/introspect",
            "/api/test/**",
            "/api/customer/checkUsername/**",
            "/api/customer/checkEmail/**",
            "/api/category/**",
            "/api/product/**",
            "/api/payments/return",
            "/graphiql",
            "/graphql",
            "/api/graphql/product",
            "/graphql-ui",
            "/webjars/**",
            "/auth/login-google", "/login-google", "/oauth2/**", "/api/v1/auth/**"
    };

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.oauth2ResourceServer(
                        oauth2 ->
                                oauth2.authenticationEntryPoint((exceptionHandler))
                                        .accessDeniedHandler(exceptionHandler))
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(
                        sm ->
                                sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(
                        auth ->
                                auth.requestMatchers(acceptedEndpoint)
                                        .permitAll()
                                        .anyRequest()
                                        .authenticated())
                .exceptionHandling(

                        ex ->
                                ex.authenticationEntryPoint(exceptionHandler).accessDeniedHandler(exceptionHandler))
                .oauth2ResourceServer(
                        oauth2 ->
                                oauth2.jwt(
                                        jwt ->
                                                jwt.decoder(
                                                        jwtDecoder()
                                                ).jwtAuthenticationConverter(jwtAuthenticationConverter())))
                .oauth2Login(oauth2 -> oauth2
                        .authorizationEndpoint(
                                authz ->
                                        authz.baseUri("/oauth2/authorize"))
                        .redirectionEndpoint(
                                redir -> redir.baseUri("/login/oauth2/code/*"))
                        .successHandler(oAuth2SuccessHandler())


                        );
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

        NimbusJwtDecoder decoder = NimbusJwtDecoder.withPublicKey(pubKey)
                .signatureAlgorithm(SignatureAlgorithm.RS512)
                .build();

        OAuth2TokenValidator<Jwt> defaultValidator = JwtValidators.createDefaultWithIssuer(props.issuer());

        OAuth2TokenValidator<Jwt> blacklistValidator = new JwtBlacklistValidator(logoutTokenService);

        decoder.setJwtValidator(new DelegatingOAuth2TokenValidator<>(defaultValidator, blacklistValidator));
        return decoder;
    }

    private AuthenticationSuccessHandler oAuth2SuccessHandler() {
        return (request, response, authentication) -> {
            OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();
            Map<String, Object> attrs = oauth2User.getAttributes();
            String email = (String) attrs.get("email");
            String name = (String) attrs.get("name");
            Customer customer = customerService.findByEmailAndCreate(email, name);

            var token = JwtUtil.generateToken(customer);

            response.setStatus(HttpServletResponse.SC_OK);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            Map<String, Object> body = Map.of(
                    "token", token
            );
            new ObjectMapper()
                    .writeValue(response.getWriter(), body);
        };
    }


}

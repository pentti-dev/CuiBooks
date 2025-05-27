// package com.example.mobileapi.config;
package com.example.mobileapi.config;

import com.example.mobileapi.service.AuthenticationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.core.*;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidatorResult;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimNames;


@Slf4j
public class JwtBlacklistValidator implements OAuth2TokenValidator<Jwt> {
    private final AuthenticationService logoutService;

    public JwtBlacklistValidator(AuthenticationService logoutService) {
        this.logoutService = logoutService;
    }

    @Override
    public OAuth2TokenValidatorResult validate(Jwt token) {
        String jwtID = token.getClaimAsString(JwtClaimNames.JTI);
        if (jwtID != null && logoutService.isTokenBlacklisted(jwtID)) {
            OAuth2Error err = new OAuth2Error(
                    "expired_token",
                    "expired_token",
                    null
            );

            return OAuth2TokenValidatorResult.failure(err);
        }
        return OAuth2TokenValidatorResult.success();
    }
}

package com.example.mobileapi.config;

import com.example.mobileapi.dto.response.ApiResponse;
import com.example.mobileapi.exception.ErrorCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.server.resource.BearerTokenErrorCodes;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class SecurityExceptionHandler implements AuthenticationEntryPoint, AccessDeniedHandler {


    private void sendErrorResponse(HttpServletResponse response, ErrorCode errorCode) throws IOException {
        response.setStatus(errorCode.getHttpStatus().value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");

        ApiResponse<?> apiResponse = ApiResponse.builder()
                .code(errorCode.getCode())
                .message(errorCode.getMessage())
                .build();

        ObjectMapper objectMapper = new ObjectMapper()
                .registerModule(new JavaTimeModule());
        response.getWriter().write(objectMapper.writeValueAsString(apiResponse));
        response.flushBuffer();
    }


    @Override
    public void commence(HttpServletRequest req,
                         HttpServletResponse resp,
                         AuthenticationException ex) throws IOException {

        ErrorCode appCode = ErrorCode.UNAUTHORIZED;

        if (ex instanceof OAuth2AuthenticationException oauth2Ex) {
            String errorCode = oauth2Ex.getError().getErrorCode();
            String description = oauth2Ex.getError().getDescription()
                    .toLowerCase();

            if ("expired_token".equals(errorCode) || description.contains("expired")) {
                appCode = ErrorCode.TOKEN_EXPIRED;
            } else if ("invalid_token".equals(errorCode)) {
                appCode = ErrorCode.INVALID_TOKEN;
            }
        }

        sendErrorResponse(resp, appCode);
    }


    @Override
    public void handle(HttpServletRequest req, HttpServletResponse resp,
                       AccessDeniedException ex) throws IOException {
        log.error("Access Denied: {}", ex.getMessage());
        sendErrorResponse(resp, ErrorCode.FORBIDDEN);
    }
}

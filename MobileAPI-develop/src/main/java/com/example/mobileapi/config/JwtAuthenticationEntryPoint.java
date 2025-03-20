package com.example.mobileapi.config;

import com.example.mobileapi.dto.response.ApiResponse;
import com.example.mobileapi.exception.ErrorCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

@Slf4j
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        ErrorCode errorCode;
        String jwtError = (String) request.getAttribute("jwtError");
        log.error(jwtError);
        if (jwtError.equals("EXPIRED")) errorCode = ErrorCode.TOKEN_EXPIRED;

        else if (jwtError.equals("INVALID")) errorCode = ErrorCode.INVALID_TOKEN;

        else errorCode = ErrorCode.UNAUTHORIZED;
        response.setStatus(errorCode.getHttpStatus().value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        ApiResponse<?> apiResponse = ApiResponse.builder()
                .code(errorCode.getErrorCode())
                .message(errorCode.getMessage())
                .build();
        ObjectMapper objectMapper = new ObjectMapper();
        response.getWriter().write(objectMapper.writeValueAsString(apiResponse));
        response.flushBuffer();
    }
}

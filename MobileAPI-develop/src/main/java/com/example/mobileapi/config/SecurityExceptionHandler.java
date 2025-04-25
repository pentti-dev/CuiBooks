package com.example.mobileapi.config;

import com.example.mobileapi.dto.response.ApiResponse;
import com.example.mobileapi.exception.ErrorCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
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

    /**
     * Xử lý lỗi 401 - Unauthorized (Người dùng chưa xác thực)
     */
    @Override
    public void commence(HttpServletRequest req, HttpServletResponse resp,
                         AuthenticationException ex) throws IOException {
        String jwtError = (String) req.getAttribute("jwtError");
        ErrorCode code;
        if (jwtError == null) {
            code = ErrorCode.UNAUTHORIZED;
        } else {
            code = switch (jwtError) {
                case "EXPIRED", "LOGOUT" -> ErrorCode.TOKEN_EXPIRED;
                case "INVALID_SIGNATURE", "UNSUPPORTED", "INVALID" -> ErrorCode.INVALID_TOKEN;
                default -> ErrorCode.UNAUTHORIZED;
            };
        }
        sendErrorResponse(resp, code);
    }

    @Override
    public void handle(HttpServletRequest req, HttpServletResponse resp,
                       AccessDeniedException ex) throws IOException {
        log.error("Access Denied: {}", ex.getMessage());
        sendErrorResponse(resp, ErrorCode.FORBIDDEN);
    }
}

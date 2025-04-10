package com.example.mobileapi.config;

import com.example.mobileapi.dto.response.ApiResponse;
import com.example.mobileapi.exception.ErrorCode;
import com.fasterxml.jackson.databind.ObjectMapper;
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

        ObjectMapper objectMapper = new ObjectMapper();
        response.getWriter().write(objectMapper.writeValueAsString(apiResponse));
        response.flushBuffer();
    }

    /**
     * Xử lý lỗi 401 - Unauthorized (Người dùng chưa xác thực)
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        ErrorCode errorCode;
        String jwtError = (String) request.getAttribute("jwtError");
        log.error("Unauthorized: {}", jwtError);

        if (jwtError == null) errorCode = ErrorCode.UNAUTHORIZED;
        else if (jwtError.equals("EXPIRED")) errorCode = ErrorCode.TOKEN_EXPIRED;
        else if (jwtError.equals("INVALID")) errorCode = ErrorCode.INVALID_TOKEN;
        else errorCode = ErrorCode.UNAUTHORIZED;

        sendErrorResponse(response, errorCode);
    }

    /**
     * Xử lý lỗi 403 - Forbidden (Người dùng không có quyền truy cập)
     */
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        log.error("Access Denied: {}", accessDeniedException.getMessage());
        sendErrorResponse(response, ErrorCode.FORBIDDEN);
    }
}

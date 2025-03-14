package com.example.mobileapi.exception;

import com.example.mobileapi.dto.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = AppException.class)
    ResponseEntity<ApiResponse<String>> handleRuntimeException(AppException e) {
        ErrorCode errorCode = e.getErrorCode();


        return ResponseEntity.badRequest()
                .body(ApiResponse.<String>builder()
                        .code(errorCode.getErrorCode())
                        .message(errorCode.getMessage()).
                        build());
    }
}

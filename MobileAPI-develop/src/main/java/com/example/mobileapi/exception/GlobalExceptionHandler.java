package com.example.mobileapi.exception;

import com.example.mobileapi.dto.response.ApiResponse;
import jakarta.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailParseException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.UnsupportedEncodingException;
import java.util.Objects;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = AppException.class)
    ResponseEntity<ApiResponse<Void>> handlingAppException(AppException e) {
        ErrorCode errorCode = e.getErrorCode();
        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(
                        ApiResponse.<Void>builder()
                                .message(errorCode.getMessage())
                                .code(errorCode.getCode())
                                .build());

    }


    @ExceptionHandler(value = AccessDeniedException.class)
    ResponseEntity<ApiResponse<Void>> handlingAccessDeDeniedException(AccessDeniedException e) {
        ErrorCode errorCode = ErrorCode.FORBIDDEN;
        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(
                        ApiResponse.<Void>builder()
                                .message(errorCode.getMessage())
                                .code(errorCode.getCode())
                                .build());
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    ResponseEntity<ApiResponse<Void>> handlingValidation(MethodArgumentNotValidException e) {
        String enumKey = Objects.requireNonNull(e.getFieldError()).getDefaultMessage();
        ErrorCode errorCode = ErrorCode.valueOf(enumKey);
        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(
                        ApiResponse.<Void>builder()
                                .message(errorCode.getMessage())
                                .code(errorCode.getCode())
                                .build());


    }

    @ExceptionHandler(value = MailParseException.class)
    ResponseEntity<ApiResponse<Void>> handlingMailParseException(MailParseException e) {
        ErrorCode errorCode = ErrorCode.ERROR_DURING_SEND_EMAIL;
        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(
                        ApiResponse.<Void>builder()
                                .message(errorCode.getMessage())
                                .code(errorCode.getCode())
                                .build());
    }

    @ExceptionHandler(value = MessagingException.class)
    ResponseEntity<ApiResponse<Void>> handlingMessagingException(MessagingException e) {
        ErrorCode errorCode = ErrorCode.ERROR_DURING_SEND_EMAIL;
        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(
                        ApiResponse.<Void>builder()
                                .message(errorCode.getMessage())
                                .code(errorCode.getCode())
                                .build());
    }

    @ExceptionHandler(value = RuntimeException.class)
    ResponseEntity<ApiResponse<Void>> handlingRuntimeException(Exception e) {
        ErrorCode errorCode = ErrorCode.INTERNAL_SERVER_ERROR;
        e.printStackTrace();
        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(
                        ApiResponse.<Void>builder()
                                .message(errorCode.getMessage())
                                .code(errorCode.getCode())
                                .build());


    }

    @ExceptionHandler(value = UnsupportedEncodingException.class)
    ResponseEntity<ApiResponse<Void>> handlingUnsupportedEncodingException(UnsupportedEncodingException e) {

        ErrorCode errorCode = ErrorCode.INTERNAL_SERVER_ERROR;
        log.error(e.getMessage());
        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(
                        ApiResponse.<Void>builder()
                                .message(errorCode.getMessage())
                                .code(errorCode.getCode())
                                .build());

    }
}

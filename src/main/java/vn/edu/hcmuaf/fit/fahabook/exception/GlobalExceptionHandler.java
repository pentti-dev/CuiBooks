package vn.edu.hcmuaf.fit.fahabook.exception;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.time.format.DateTimeParseException;
import java.util.Map;
import java.util.Objects;

import jakarta.mail.MessagingException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;

import org.springframework.graphql.execution.DataFetcherExceptionResolverAdapter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.lang.NonNull;
import org.springframework.mail.MailParseException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;

import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import graphql.schema.DataFetchingEnvironment;
import lombok.extern.slf4j.Slf4j;
import vn.edu.hcmuaf.fit.fahabook.dto.response.ApiResponse;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends DataFetcherExceptionResolverAdapter {
    @ExceptionHandler(value = AppException.class)
    ResponseEntity<ApiResponse<Void>> handlingAppException(AppException e) {

        ErrorCode errorCode = e.getErrorCode();
        if (errorCode == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.<Void>builder()
                            .message(e.getMessage())
                            .code(HttpStatus.BAD_REQUEST.value())
                            .build());
        }
        return ResponseEntity.status(errorCode.getHttpStatus())
                .body(ApiResponse.<Void>builder()
                        .message(errorCode.getMessage())
                        .code(errorCode.getCode())
                        .build());
    }

    @ExceptionHandler(value = AccessDeniedException.class)
    ResponseEntity<ApiResponse<Void>> handlingAccessDeDeniedException(AccessDeniedException e) {
        ErrorCode errorCode = ErrorCode.FORBIDDEN;
        return ResponseEntity.status(errorCode.getHttpStatus())
                .body(ApiResponse.<Void>builder()
                        .message(errorCode.getMessage())
                        .code(errorCode.getCode())
                        .build());
    }

    @ExceptionHandler(value = ConstraintViolationException.class)
    ResponseEntity<ApiResponse<Void>> handlingConstraintViolationException(ConstraintViolationException e) {
        var violation = e.getConstraintViolations();
        ErrorCode errorCode = violation.stream()
                .map(ConstraintViolation::getMessage)
                .map(ErrorCode::valueOf)
                .findFirst()
                .orElse(ErrorCode.INVALID_DATA);
        return ResponseEntity.status(errorCode.getHttpStatus())
                .body(ApiResponse.<Void>builder()
                        .message(errorCode.getMessage())
                        .code(errorCode.getCode())
                        .build());
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    ResponseEntity<ApiResponse<Void>> handlingValidation(MethodArgumentNotValidException e) {
        String enumKey = Objects.requireNonNull(e.getFieldError()).getDefaultMessage();
        ErrorCode errorCode = ErrorCode.valueOf(enumKey);
        return ResponseEntity.status(errorCode.getHttpStatus())
                .body(ApiResponse.<Void>builder()
                        .message(errorCode.getMessage())
                        .code(errorCode.getCode())
                        .build());
    }

    @ExceptionHandler(value = MailParseException.class)
    ResponseEntity<ApiResponse<Void>> handlingMailParseException(MailParseException e) {
        ErrorCode errorCode = ErrorCode.ERROR_DURING_SEND_EMAIL;
        return ResponseEntity.status(errorCode.getHttpStatus())
                .body(ApiResponse.<Void>builder()
                        .message(errorCode.getMessage())
                        .code(errorCode.getCode())
                        .build());
    }

    @ExceptionHandler(value = MessagingException.class)
    ResponseEntity<ApiResponse<Void>> handlingMessagingException(MessagingException e) {
        ErrorCode errorCode = ErrorCode.ERROR_DURING_SEND_EMAIL;
        return ResponseEntity.status(errorCode.getHttpStatus())
                .body(ApiResponse.<Void>builder()
                        .message(errorCode.getMessage())
                        .code(errorCode.getCode())
                        .build());
    }

    @ExceptionHandler(value = IOException.class)
    ResponseEntity<ApiResponse<Void>> handlingIOException(IOException e) {
        ErrorCode errorCode = ErrorCode.INTERNAL_SERVER_ERROR;
        log.error(e.getMessage(), e);
        return ResponseEntity.status(errorCode.getHttpStatus())
                .body(ApiResponse.<Void>builder()
                        .message(e.getMessage())
                        .code(errorCode.getCode())
                        .build());
    }

    @ExceptionHandler(value = RuntimeException.class)
    ResponseEntity<ApiResponse<Void>> handlingRuntimeException(Exception e) {
        ErrorCode errorCode = ErrorCode.INTERNAL_SERVER_ERROR;
        return ResponseEntity.status(errorCode.getHttpStatus())
                .body(ApiResponse.<Void>builder()
                        .message(e.getMessage())
                        .code(errorCode.getCode())
                        .build());
    }

    @ExceptionHandler(value = UnsupportedEncodingException.class)
    ResponseEntity<ApiResponse<Void>> handlingUnsupportedEncodingException(UnsupportedEncodingException e) {

        ErrorCode errorCode = ErrorCode.INTERNAL_SERVER_ERROR;
        log.error(e.getMessage());
        return ResponseEntity.status(errorCode.getHttpStatus())
                .body(ApiResponse.<Void>builder()
                        .message(e.getMessage())
                        .code(errorCode.getCode())
                        .build());
    }

    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponse<Void>> handlingHttpMessageNotReadableException(
            HttpMessageNotReadableException e) {
        Throwable cause = e.getCause();
        if (cause instanceof DateTimeParseException) {
            ErrorCode errorCode = ErrorCode.INVALID_DATE_FORMAT;
            return ResponseEntity.status(errorCode.getHttpStatus())
                    .body(ApiResponse.<Void>builder()
                            .message(errorCode.getMessage())
                            .code(errorCode.getCode())
                            .build());
        }
        if (cause instanceof InvalidFormatException) {
            ErrorCode errorCode = ErrorCode.INVALID_DATA;
            log.error("Lỗi: {}", cause.getMessage(), e);
            return ResponseEntity.status(errorCode.getHttpStatus())
                    .body(ApiResponse.<Void>builder()
                            .message(errorCode.getMessage())
                            .code(errorCode.getCode())
                            .build());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.<Void>builder().message(e.getMessage()).build());
    }

    // --------------------------------------------------------------GRAPHQL----------------------------------------------------------------------------
    @Override
    protected GraphQLError resolveToSingleError(@NonNull Throwable ex, @NonNull DataFetchingEnvironment env) {
        if (ex instanceof AppException appException) {
            ErrorCode errorCode = appException.getErrorCode();
            return GraphqlErrorBuilder.newError(env)
                    .message(errorCode.getMessage())
                    .extensions(Map.of(
                            "code", errorCode.getCode(),
                            "httpStatus", errorCode.getHttpStatus().value()))
                    .build();
        }
        if (ex instanceof ConstraintViolationException ce) {
            var violation = ce.getConstraintViolations();
            ErrorCode errorCode = violation.stream()
                    .map(ConstraintViolation::getMessage)
                    .map(ErrorCode::valueOf)
                    .findFirst()
                    .orElse(ErrorCode.INVALID_DATA);
            return GraphqlErrorBuilder.newError(env)
                    .message(errorCode.getMessage())
                    .extensions(Map.of(
                            "code", errorCode.getCode(),
                            "httpStatus", errorCode.getHttpStatus()))
                    .build();
        }

        log.error("", ex);
        return GraphqlErrorBuilder.newError(env).message(ex.getMessage()).build();
    }
}

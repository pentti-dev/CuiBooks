// ApiResponse.java
package com.example.mobileapi.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.apache.http.HttpStatus;

import java.time.Instant;

@Setter
@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {
    Integer code;
    String message;
    @Builder.Default
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    Instant timestamp = Instant.now();
    T data;


    public static <T> ApiResponse<T> success(String message) {
        return ApiResponse.<T>builder()
                .code(HttpStatus.SC_OK)
                .message(message)
                .build();
    }

    public static <T> ApiResponse<T> success() {
        return ApiResponse.<T>builder()
                .code(HttpStatus.SC_OK)
                .build();
    }

    public static <T> ApiResponse<T> success(String message, T data) {
        return ApiResponse.<T>builder()
                .code(HttpStatus.SC_OK)
                .message(message)
                .data(data)
                .build();
    }

    public static <T> ApiResponse<T> success(T data) {
        return ApiResponse.<T>builder()
                .code(HttpStatus.SC_OK)
                .data(data)
                .build();
    }

    public static <T> ApiResponse<T> error(Integer code, String message) {
        return ApiResponse.<T>builder()
                .code(code)
                .message(message)
                .build();
    }


}

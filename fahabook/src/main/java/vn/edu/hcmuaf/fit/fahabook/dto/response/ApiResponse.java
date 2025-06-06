// ApiResponse.java
package vn.edu.hcmuaf.fit.fahabook.dto.response;

import java.time.Instant;

import org.apache.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.*;
import lombok.experimental.FieldDefaults;

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
        return ApiResponse.<T>builder().code(HttpStatus.SC_OK).message(message).build();
    }

    public static <T> ApiResponse<T> success() {
        return ApiResponse.<T>builder().code(HttpStatus.SC_OK).build();
    }

    public static <T> ApiResponse<T> success(String message, T data) {
        return ApiResponse.<T>builder()
                .code(HttpStatus.SC_OK)
                .message(message)
                .data(data)
                .build();
    }

    public static <T> ApiResponse<T> success(T data) {
        return ApiResponse.<T>builder().code(HttpStatus.SC_OK).data(data).build();
    }

    public static <T> ApiResponse<T> error(Integer code, String message) {
        return ApiResponse.<T>builder().code(code).message(message).build();
    }
}

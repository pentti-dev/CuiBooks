package com.example.mobileapi.dto.request;

import jakarta.validation.constraints.*;
import lombok.Getter;

import java.util.UUID;

@Getter
public class RatingRequestDTO {
    @NotNull(message = "MISSING_PRODUCT")
    UUID productId;
    @NotNull(message = "MISSING_CUSTOMER")
    UUID customerId;

    @NotBlank(message = "MISSING_COMMENT")
    @Size(min = 10, max = 500, message = "INVALID_COMMENT_LENGTH")
    String comment;

    @Min(value = 1, message = "INVALID_SCORE")
    @Max(value = 5, message = "INVALID_SCORE")
    Integer score;
}

package com.example.mobileapi.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DiscountDTO {
    @Size(max = 10, message = "INVALID_CODE_DISCOUNT")
    @NotBlank(message = "MISSING_CODE_DISCOUNT")
    String code;
    @Min(value = 1, message = "INVALID_PERCENT")
    @Max(value = 100, message =
            "INVALID_PERCENT")
    @NotNull(message = "MISSING_PERCENT")
    Integer percent;
    @NotNull(message = "MISSING_START_DATE")
    @FutureOrPresent(message = "INVALID_START_DATE")
    LocalDate startDate;

    @NotNull(message = "MISSING_END_DATE")
    @Future(message = "INVALID_END_DATE")
    LocalDate endDate;
    Boolean active;

}
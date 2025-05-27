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

    LocalDate startDate;


    LocalDate endDate;
    Boolean active;

}
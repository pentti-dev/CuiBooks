package com.example.mobileapi.dto.base;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
public class DiscountDTO {
    protected UUID id;
    protected String code;
    protected Integer percent;
    @FutureOrPresent(message = "INVALID_START_DATE")
    protected LocalDate startDate;

    @Future(message = "INVALID_END_DATE")
    protected LocalDate endDate;
    protected Boolean active;


}

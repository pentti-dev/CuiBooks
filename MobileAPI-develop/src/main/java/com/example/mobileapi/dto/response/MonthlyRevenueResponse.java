package com.example.mobileapi.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@Getter
public class MonthlyRevenueResponse {
    @Min(1)
    @Max(12)
    int month;
    BigDecimal revenue;

}

package com.example.mobileapi.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

import jakarta.validation.constraints.Size;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@Getter
public class MonthlyRevenueResponse {
    @Size(min = 1, max = 12)
    Long month;
    BigDecimal revenue;

}

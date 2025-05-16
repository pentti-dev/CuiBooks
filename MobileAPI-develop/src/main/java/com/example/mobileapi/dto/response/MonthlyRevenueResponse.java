package com.example.mobileapi.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@Getter
public class MonthlyRevenueResponse {
    int month;
    BigDecimal revenue;


}

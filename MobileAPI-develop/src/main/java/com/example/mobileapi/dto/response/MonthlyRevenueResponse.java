package com.example.mobileapi.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@Getter
public class MonthlyRevenueResponse {
    int month;
    long revenue;


}

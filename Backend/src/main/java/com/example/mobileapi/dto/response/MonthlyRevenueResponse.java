package com.example.mobileapi.dto.response;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MonthlyRevenueResponse {
    int month;
    long revenue;

    public MonthlyRevenueResponse(int month, long revenue) {
        this.month = month;
        this.revenue = revenue;
    }
}

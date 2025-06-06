package com.example.mobileapi.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@Getter
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class RevenueResponse {
    @Min(1)
    @Max(12)
    int month;
    int day;
    int year;
    BigDecimal revenue;

}

package vn.edu.hcmuaf.fit.fahabook.dto.response;

import java.math.BigDecimal;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.*;
import lombok.experimental.FieldDefaults;

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

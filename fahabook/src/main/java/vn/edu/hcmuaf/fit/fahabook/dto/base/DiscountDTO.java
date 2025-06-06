package vn.edu.hcmuaf.fit.fahabook.dto.base;

import java.time.LocalDate;
import java.util.UUID;

import jakarta.validation.constraints.*;

import lombok.Getter;
import lombok.Setter;

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

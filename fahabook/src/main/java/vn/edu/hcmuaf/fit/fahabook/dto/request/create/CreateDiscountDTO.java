package vn.edu.hcmuaf.fit.fahabook.dto.request.create;

import java.time.LocalDate;
import java.util.UUID;

import jakarta.validation.constraints.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;
import vn.edu.hcmuaf.fit.fahabook.dto.base.DiscountDTO;

@Getter
@Setter
public class CreateDiscountDTO extends DiscountDTO {
    @JsonIgnore
    @Override
    public UUID getId() {
        return super.getId();
    }

    @Override
    @NotBlank(message = "MISSING_CODE_DISCOUNT")
    @Size(max = 10, message = "INVALID_CODE_DISCOUNT")
    public String getCode() {
        return super.getCode();
    }

    @Override
    @NotNull(message = "MISSING_PERCENT")
    @Min(value = 1, message = "INVALID_PERCENT")
    @Max(value = 100, message = "INVALID_PERCENT")
    public Integer getPercent() {
        return super.getPercent();
    }

    @Override
    @NotNull(message = "MISSING_START_DATE")
    public LocalDate getStartDate() {
        return super.getStartDate();
    }

    @Override
    @NotNull(message = "MISSING_END_DATE")
    public LocalDate getEndDate() {
        return super.getEndDate();
    }
}

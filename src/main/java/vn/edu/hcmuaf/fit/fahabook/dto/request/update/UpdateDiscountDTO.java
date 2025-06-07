package vn.edu.hcmuaf.fit.fahabook.dto.request.update;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;
import vn.edu.hcmuaf.fit.fahabook.dto.base.DiscountDTO;

@Getter
@Setter
public class UpdateDiscountDTO extends DiscountDTO {
    @JsonIgnore
    @Override
    public UUID getId() {
        return super.getId();
    }

    @NotBlank(message = "MISSING_CODE_DISCOUNT")
    @Override
    public String getCode() {
        return super.getCode();
    }
}

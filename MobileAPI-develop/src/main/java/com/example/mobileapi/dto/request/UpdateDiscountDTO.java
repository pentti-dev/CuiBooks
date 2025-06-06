package com.example.mobileapi.dto.request;

import com.example.mobileapi.dto.base.DiscountDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

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

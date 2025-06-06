package vn.edu.hcmuaf.fit.fahabook.dto.request;

import java.util.UUID;

import jakarta.validation.constraints.Min;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderDetailRequestDTO {
    UUID productId;

    @Min(value = 1, message = "QUANTITY_MUST_BE_POSITIVE")
    Integer quantity;
}

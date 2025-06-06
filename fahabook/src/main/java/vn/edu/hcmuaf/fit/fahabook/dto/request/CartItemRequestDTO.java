package vn.edu.hcmuaf.fit.fahabook.dto.request;

import java.util.UUID;

import jakarta.validation.constraints.Min;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CartItemRequestDTO {
    UUID cartId;
    UUID productId;

    @Min(value = 1, message = "QUANTITY_MUST_BE_POSITIVE")
    int quantity;
}

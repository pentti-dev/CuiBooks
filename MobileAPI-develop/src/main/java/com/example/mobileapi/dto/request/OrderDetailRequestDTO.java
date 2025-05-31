package com.example.mobileapi.dto.request;

import jakarta.validation.constraints.Min;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderDetailRequestDTO {
    UUID productId;
    @Min(value = 1, message = "QUANTITY_MUST_BE_POSITIVE")
    Integer quantity;
}

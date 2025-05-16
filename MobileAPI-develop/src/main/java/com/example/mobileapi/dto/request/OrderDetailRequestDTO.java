package com.example.mobileapi.dto.request;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderDetailRequestDTO {
    UUID productId;
    Integer quantity;
}

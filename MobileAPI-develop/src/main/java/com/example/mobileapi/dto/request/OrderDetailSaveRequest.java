package com.example.mobileapi.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderDetailSaveRequest {
    UUID productId;
    UUID orderId;
    Integer quantity;
}

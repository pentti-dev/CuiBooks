package com.example.mobileapi.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderDetailSaveRequest {
    Integer productId;
    Integer orderId;
    Integer quantity;
}

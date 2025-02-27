package com.example.mobileapi.dto.request;

import lombok.Data;

@Data
public class OrderDetailSaveRequest {
    private Integer productId;
    private Integer orderId;
    private Integer quantity;
}

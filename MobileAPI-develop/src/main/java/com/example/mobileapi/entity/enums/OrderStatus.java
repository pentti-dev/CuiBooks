package com.example.mobileapi.entity.enums;

import lombok.Getter;

@Getter
public enum OrderStatus {
    PENDING_PAYMENT,
    PENDING,
    SHIPPING,
    DELIVERED,
    CANCELLED,
    PAYMENT_SUCCESS,
    PAYMENT_FAILED;

}

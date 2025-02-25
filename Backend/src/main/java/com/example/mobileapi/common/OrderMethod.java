package com.example.mobileapi.common;

import lombok.Getter;

@Getter
public enum OrderMethod {
    COD("Thanh toán khi nhận hàng"),
    VN_PAY("Thanh toán qua VNPay"),
    MOMO("Thanh toán qua Momo"),
    ZALO_PAY("Thanh toán qua ZaloPay");
    private final String value;

    OrderMethod(String value) {
        this.value = value;
    }
}

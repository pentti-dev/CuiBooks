package com.example.mobileapi.service;

import com.example.mobileapi.dto.response.PaymentResponse;
import com.example.mobileapi.exception.AppException;

import java.math.BigDecimal;
import java.util.UUID;

public interface PaymentService {

    PaymentResponse createVNPayPayment(UUID orderId, BigDecimal price) throws AppException;

    boolean notifyOrder(String vnp_ResponseCode, String vnp_TxnRef, String vnp_TransactionNo,
            String vnp_TransactionDate, String vnp_Amount);

}

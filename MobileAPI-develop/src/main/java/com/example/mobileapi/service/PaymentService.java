package com.example.mobileapi.service;

import com.example.mobileapi.dto.response.PaymentResponse;
import com.example.mobileapi.entity.enums.OrderStatus;
import com.example.mobileapi.exception.AppException;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

public interface PaymentService {
    PaymentResponse createVNPayPayment(Integer orderId, Long price) throws AppException;


    boolean notifyOrder(String vnp_ResponseCode, String vnp_TxnRef, String vnp_TransactionNo, String vnp_TransactionDate, String vnp_Amount);

}

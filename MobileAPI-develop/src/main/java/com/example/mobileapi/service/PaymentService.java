package com.example.mobileapi.service;

import com.example.mobileapi.dto.response.PaymentResponse;
import com.example.mobileapi.exception.AppException;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

public interface PaymentService {
    PaymentResponse createVNPayPayment(Integer orderId, Long price) throws AppException;

}

package vn.edu.hcmuaf.fit.fahabook.service;

import java.util.UUID;

import vn.edu.hcmuaf.fit.fahabook.dto.response.PaymentResponse;
import vn.edu.hcmuaf.fit.fahabook.exception.AppException;

public interface PaymentService {

    PaymentResponse createVNPayPayment(UUID orderId, String returnUrl) throws AppException;

    boolean notifyOrder(
            String vnpResponseCode,
            String vnpTransactionStatus,
            String orderId,
            String vnpTransactionNo,
            String vnpTransactionDate,
            String vnpAmount);
}

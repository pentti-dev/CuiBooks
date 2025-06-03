package com.example.mobileapi.controller;

import com.example.mobileapi.dto.request.CreatePaymentRequest;
import com.example.mobileapi.dto.response.ApiResponse;
import com.example.mobileapi.dto.response.PaymentResponse;
import com.example.mobileapi.service.OrderService;
import com.example.mobileapi.service.PaymentService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
@Tag(name = "Payment", description = "Payment API")
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class PaymentController {

    PaymentService paymentService;


    @PostMapping("/create/{orderId}")
    public ResponseEntity<PaymentResponse> createPayment(
            @PathVariable UUID orderId,
            @RequestBody CreatePaymentRequest request
    ) {
        String returnUrl = request.getReturnUrl();
        PaymentResponse response = paymentService.createVNPayPayment(orderId, returnUrl);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/vnpay-return")
    public ResponseEntity<String> vnpayReturnHandler(
            @RequestParam("vnp_ResponseCode") String responseCode,
            @RequestParam("vnp_TransactionStatus") String transactionStatus,
            @RequestParam("vnp_TxnRef") String orderId,
            @RequestParam("vnp_TransactionNo") String transactionNo,
            @RequestParam("vnp_PayDate") String transactionDate,
            @RequestParam("vnp_Amount") String amount
    ) {
        boolean result = paymentService.notifyOrder(
                responseCode,
                transactionStatus,
                orderId,
                transactionNo,
                transactionDate,
                amount
        );

        return result
                ? ResponseEntity.ok("Payment success")
                : ResponseEntity.badRequest().body("Payment failed");
    }

}
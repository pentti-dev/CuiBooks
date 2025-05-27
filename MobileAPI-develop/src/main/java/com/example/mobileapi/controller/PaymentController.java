package com.example.mobileapi.controller;

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
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<PaymentResponse> createPayment(@PathVariable("orderId") UUID orderId) {
        var resp = paymentService.createVNPayPayment(orderId);
        return ApiResponse.success(resp);
    }

    @GetMapping("/return")
    public ApiResponse<?> notifyOrder(HttpServletResponse response, @RequestParam String vnp_Amount, @RequestParam String vnp_BankCode, @RequestParam(required = false) String vnp_BankTranNo, @RequestParam String vnp_CardType, @RequestParam String vnp_OrderInfo, @RequestParam String vnp_PayDate, @RequestParam String vnp_ResponseCode, @RequestParam String vnp_TmnCode, @RequestParam String vnp_TransactionNo, @RequestParam String vnp_TransactionStatus, @RequestParam String vnp_TxnRef, @RequestParam String vnp_SecureHash) {

        // Gọi service với các tham số cần thiết
        boolean isSuccess = paymentService.notifyOrder(vnp_ResponseCode, vnp_TxnRef, vnp_TransactionNo, vnp_PayDate, vnp_Amount);

        return ApiResponse.<String>builder().message(isSuccess ? "Thanh toán thành công" : "Thanh toán thất bại").build();
    }
}

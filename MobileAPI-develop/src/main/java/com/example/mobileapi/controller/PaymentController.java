package com.example.mobileapi.controller;

import com.example.mobileapi.dto.response.ApiResponse;
import com.example.mobileapi.dto.response.PaymentResponse;
import com.example.mobileapi.service.PaymentService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
@Validated
@Tag(name = "Payment", description = "Payment API")
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<PaymentResponse> createPayment(
            @PathParam("price") Long price, @PathParam("orderId") Integer orderId
    ) throws UnsupportedEncodingException {

        var resp = paymentService.createVNPayPayment(orderId, price);
        return ApiResponse.<PaymentResponse>builder()
                .data(resp)
                .message("Payment URL generated")
                .build();
    }

    @GetMapping("/notify")
    public ApiResponse<?> notifyOrder(HttpServletResponse response,
                                      @RequestParam String vnp_Amount,
                                      @RequestParam String vnp_BankCode,
                                      @RequestParam(required = false) String vnp_BankTranNo,
                                      @RequestParam String vnp_CardType,
                                      @RequestParam String vnp_OrderInfo,
                                      @RequestParam String vnp_PayDate,
                                      @RequestParam String vnp_ResponseCode,
                                      @RequestParam String vnp_TmnCode,
                                      @RequestParam String vnp_TransactionNo,
                                      @RequestParam String vnp_TransactionStatus,
                                      @RequestParam String vnp_TxnRef,
                                      @RequestParam String vnp_SecureHash
    ) {

        // Gọi service với các tham số cần thiết
        boolean isSuccess = paymentService.notifyOrder(
                vnp_ResponseCode,
                vnp_TxnRef,
                vnp_TransactionNo,
                vnp_PayDate,
                vnp_Amount
        );

        return ApiResponse.<String>builder()
                .message(isSuccess ? "Thanh toán thành công" : "Thanh toán thất bại")
                .build();
    }
}

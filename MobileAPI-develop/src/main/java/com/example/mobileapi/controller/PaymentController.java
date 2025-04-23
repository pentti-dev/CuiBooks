package com.example.mobileapi.controller;

import com.example.mobileapi.dto.response.ApiResponse;
import com.example.mobileapi.dto.response.PaymentResponse;
import com.example.mobileapi.service.PaymentService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

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

//    @GetMapping("/callback")
//    public ApiResponse<String> callback(@Parameter(hidden = true) HttpServletRequest request) {
//        boolean ok = paymentService.verifyPaymentReturn(request);
//        return ApiResponse.<String>builder()
//                .message(ok ? "Payment validated" : "Validation error")
//                .data(ok ? "Thanh toán thành công" : "Thanh toán thất bại")
//                .build();
//    }
}

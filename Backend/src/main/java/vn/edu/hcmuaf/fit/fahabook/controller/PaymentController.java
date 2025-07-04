package vn.edu.hcmuaf.fit.fahabook.controller;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import vn.edu.hcmuaf.fit.fahabook.dto.request.CreatePaymentRequest;
import vn.edu.hcmuaf.fit.fahabook.dto.response.PaymentResponse;
import vn.edu.hcmuaf.fit.fahabook.service.PaymentService;

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
            @PathVariable UUID orderId, @RequestBody CreatePaymentRequest request) {
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
            @RequestParam("vnp_Amount") String amount) {
        boolean result = paymentService.notifyOrder(
                responseCode, transactionStatus, orderId, transactionNo, transactionDate, amount);

        return result
                ? ResponseEntity.ok("Payment success")
                : ResponseEntity.badRequest().body("Payment failed");
    }
}

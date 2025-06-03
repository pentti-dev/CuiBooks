package com.example.mobileapi.service.impl;

import com.example.mobileapi.config.props.VnPayProperties;
import com.example.mobileapi.dto.response.PaymentResponse;
import com.example.mobileapi.entity.enums.OrderStatus;
import com.example.mobileapi.exception.AppException;
import com.example.mobileapi.exception.ErrorCode;
import com.example.mobileapi.service.*;
import com.example.mobileapi.util.VnPayUtil;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PaymentServiceImpl implements PaymentService {
    @Delegate
    OrderService orderService;
    VnPayProperties vnPayProperties;
    VnPayUtil vnPayUtil;
    TransactionService transactionService;

    @Override
    public PaymentResponse createVNPayPayment(UUID orderId, String returnUrl) throws AppException {
        if (!orderService.existById(orderId)) {
            throw new AppException(ErrorCode.ORDER_NOT_FOUND);
        }

        BigDecimal price = orderService.getPriceByOrderId(orderId);
        log.info("Đơn hàng: {}, giá: {}", orderId, price);

        String orderType = "other";
        String bankCode = vnPayProperties.bankCode();
        String vnp_TxnRef = String.valueOf(orderId);
        String vnp_IpAddr = vnPayUtil.getClientIpAddress();

        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", vnPayProperties.version());
        vnp_Params.put("vnp_Command", vnPayProperties.command());
        vnp_Params.put("vnp_TmnCode", vnPayProperties.tmnCode());

        BigDecimal amount = price.movePointRight(2);
        vnp_Params.put("vnp_Amount", amount.setScale(0, RoundingMode.UNNECESSARY).toPlainString());
        vnp_Params.put("vnp_CurrCode", vnPayProperties.currencyCode());

        if (bankCode != null && !bankCode.isEmpty()) {
            vnp_Params.put("vnp_BankCode", bankCode);
        }

        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", "Thanh toan don hang:" + vnp_TxnRef);
        vnp_Params.put("vnp_OrderType", orderType);
        vnp_Params.put("vnp_Locale", vnPayProperties.locale());

        // ✅ Dùng returnUrl truyền vào thay vì lấy từ properties
        vnp_Params.put("vnp_ReturnUrl", "http://localhost:3000/payment-return");

        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        vnp_Params.put("vnp_CreateDate", formatter.format(cld.getTime()));
        cld.add(Calendar.MINUTE, 15);
        vnp_Params.put("vnp_ExpireDate", formatter.format(cld.getTime()));

        List<String> fieldNames = new ArrayList<>(vnp_Params.keySet());
        Collections.sort(fieldNames);

        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();

        for (Iterator<String> itr = fieldNames.iterator(); itr.hasNext(); ) {
            String fieldName = itr.next();
            String fieldValue = vnp_Params.get(fieldName);
            if (fieldValue != null && !fieldValue.isEmpty()) {
                hashData.append(fieldName).append('=').append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII));
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII))
                        .append('=')
                        .append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII));
                if (itr.hasNext()) {
                    hashData.append('&');
                    query.append('&');
                }
            }
        }

        String vnp_SecureHash = vnPayUtil.hmacSHA512(vnPayProperties.hashSecret(), hashData.toString());
        query.append("&vnp_SecureHash=").append(vnp_SecureHash);

        String paymentUrl = vnPayProperties.payUrl() + "?" + query;
        return PaymentResponse.builder().url(paymentUrl).build();
    }


    @Override
    public boolean notifyOrder(
            String vnpResponseCode,
            String vnpTransactionStatus,
            String orderId,
            String vnpTransactionNo,
            String vnpTransactionDate,
            String vnpAmount
    ) {
        try {
            UUID orderUUID = UUID.fromString(orderId);
            UUID transactionId = UUID.nameUUIDFromBytes(orderId.getBytes(StandardCharsets.UTF_8));

            // ✅ Sử dụng vnpTransactionStatus để kiểm tra
            boolean isSuccess = "00".equals(vnpTransactionStatus);

            OrderStatus newStatus = isSuccess
                    ? OrderStatus.PAYMENT_SUCCESS
                    : OrderStatus.PAYMENT_FAILED;

            orderService.changeOrderStatus(orderUUID, newStatus);

            transactionService.createTransaction(
                    transactionId, orderUUID,
                    vnpResponseCode,
                    vnpTransactionDate,
                    vnpAmount
            );

            log.info("VNPay call: orderId={}, status={}, txnNo={}, amount={}",
                    orderId, newStatus, vnpTransactionNo, vnpAmount);

            return isSuccess;
        } catch (Exception e) {
            log.error("notifyOrder error", e);
            return false;
        }
    }

}

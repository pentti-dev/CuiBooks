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
    public PaymentResponse createVNPayPayment(UUID orderId) throws AppException {
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
        // Điều chỉnh lại giá cho phù hợp
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
        vnp_Params.put("vnp_ReturnUrl", vnPayProperties.returnUrl());
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

        cld.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

        List fieldNames = new ArrayList(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = (String) itr.next();
            String fieldValue = (String) vnp_Params.get(fieldName);
            if ((fieldValue != null) && (!fieldValue.isEmpty())) {
                // Build hash data
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII));
                // Build query
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII));
                query.append('=');
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII));
                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }
        String queryUrl = query.toString();
        String vnp_SecureHash = vnPayUtil.hmacSHA512(vnPayProperties.hashSecret(), hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        String paymentUrl = vnPayProperties.payUrl() + "?" + queryUrl;
        return PaymentResponse.builder().url(paymentUrl).build();
    }

    @Override
    public boolean notifyOrder(
            String vnpResponseCode,
            String orderId,
            String vnpTransactionNo,
            String vnpTransactionDate,
            String vnpAmount) {
        try {
            UUID orderUUID = UUID.fromString(orderId);

            UUID transactionId = UUID.nameUUIDFromBytes(
                    orderId.getBytes(StandardCharsets.UTF_8));

            boolean isSuccess = "00".equals(vnpResponseCode);
            OrderStatus newStatus = isSuccess
                    ? OrderStatus.PAYMENT_SUCCESS
                    : OrderStatus.PAYMENT_FAILED;

            orderService.changeOrderStatus(orderUUID, newStatus);

            transactionService.createTransaction(
                    transactionId, orderUUID,
                    vnpResponseCode,
                    vnpTransactionDate,
                    vnpAmount);

            log.info(
                    "VNPay call: orderId={}, status={}, txnNo={}, amount={}",
                    orderId, newStatus, vnpTransactionNo, vnpAmount);

            return isSuccess;
        } catch (IllegalArgumentException e) {
            // orderId không phải UUID hợp lệ
            log.error("notifyOrder failed: invalid orderId '{}'", orderId, e);
            return false;
        } catch (Exception e) {
            // Bắt mọi exception khác (CSDL, service, v.v.)
            log.error("notifyOrder encountered unexpected error for orderId '{}'", orderId, e);
            return false;
        }
    }

}

package com.example.mobileapi.service.impl;

import com.example.mobileapi.config.props.VnPayProperties;
import com.example.mobileapi.dto.response.PaymentResponse;
import com.example.mobileapi.entity.enums.OrderStatus;
import com.example.mobileapi.exception.AppException;
import com.example.mobileapi.exception.ErrorCode;
import com.example.mobileapi.service.OrderService;
import com.example.mobileapi.service.PaymentService;
import com.example.mobileapi.service.TransactionService;
import com.example.mobileapi.util.VnPayUtil;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PaymentServiceImpl implements PaymentService {
    OrderService orderService;
    VnPayProperties vnPayProperties;
    VnPayUtil vnPayUtil;
    TransactionService transactionService;

    @Override
    public PaymentResponse createVNPayPayment(UUID orderId, BigDecimal price) throws AppException {

        if (!orderService.existById(orderId)) {
            throw new AppException(ErrorCode.ORDER_NOT_FOUND);
        }

        String orderType = "other";
        BigDecimal amount = price;
        String bankCode = vnPayProperties.bankCode();

        String vnp_TxnRef = String.valueOf(orderId);
        String vnp_IpAddr = vnPayUtil.getClientIpAddress();


        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", vnPayProperties.version());
        vnp_Params.put("vnp_Command", vnPayProperties.command());
        vnp_Params.put("vnp_TmnCode", vnPayProperties.tmnCode());
        vnp_Params.put("vnp_Amount", String.valueOf(amount));
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
                //Build hash data
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII));
                //Build query
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
        return PaymentResponse.builder()
                .url(paymentUrl)
                .build();
    }


    @Override
    public boolean notifyOrder(String vnp_ResponseCode, String vnp_TxnRef, String vnp_TransactionNo, String vnp_TransactionDate, String vnp_Amount) {
        if (vnp_ResponseCode.equals("00")) {
            orderService.changeOrderStatus(UUID.fromString(vnp_TxnRef), OrderStatus.PAYMENT_SUCCESS);
            transactionService.createTransaction(vnp_TransactionNo, UUID.fromString(vnp_TxnRef), vnp_ResponseCode, vnp_TransactionDate, vnp_Amount);
            return true;
        } else {
            transactionService.createTransaction(vnp_TransactionNo, UUID.fromString(vnp_TxnRef), vnp_ResponseCode, vnp_TransactionDate, vnp_Amount);
            orderService.changeOrderStatus(UUID.fromString(vnp_TxnRef), OrderStatus.PAYMENT_FAILED);
            return false;
        }
    }


}

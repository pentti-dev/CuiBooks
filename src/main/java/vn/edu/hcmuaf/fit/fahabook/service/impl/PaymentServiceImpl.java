package vn.edu.hcmuaf.fit.fahabook.service.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import vn.edu.hcmuaf.fit.fahabook.config.props.VnPayProperties;
import vn.edu.hcmuaf.fit.fahabook.dto.response.OrderResponseDTO;
import vn.edu.hcmuaf.fit.fahabook.dto.response.PaymentResponse;
import vn.edu.hcmuaf.fit.fahabook.entity.Order;
import vn.edu.hcmuaf.fit.fahabook.entity.enums.OrderMethod;
import vn.edu.hcmuaf.fit.fahabook.entity.enums.OrderStatus;
import vn.edu.hcmuaf.fit.fahabook.exception.AppException;
import vn.edu.hcmuaf.fit.fahabook.exception.ErrorCode;
import vn.edu.hcmuaf.fit.fahabook.service.OrderService;
import vn.edu.hcmuaf.fit.fahabook.service.PaymentService;
import vn.edu.hcmuaf.fit.fahabook.service.TransactionService;
import vn.edu.hcmuaf.fit.fahabook.util.VnPayUtil;

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
        OrderResponseDTO order = orderService.getOrder(orderId);
        validateOrder(order);

        BigDecimal price = orderService.getPriceByOrderId(orderId);
        log.info("Đơn hàng: {}, giá: {}", orderId, price);

        String orderType = "other";
        String bankCode = vnPayProperties.bankCode();
        String vnpTxnRef = String.valueOf(orderId);
        String vnpIpAddr = vnPayUtil.getClientIpAddress();

        Map<String, String> vnpParams = new HashMap<>();
        vnpParams.put("vnp_Version", vnPayProperties.version());
        vnpParams.put("vnp_Command", vnPayProperties.command());
        vnpParams.put("vnp_TmnCode", vnPayProperties.tmnCode());

        BigDecimal amount = price.movePointRight(2);
        vnpParams.put("vnp_Amount", amount.setScale(0, RoundingMode.UNNECESSARY).toPlainString());
        vnpParams.put("vnp_CurrCode", vnPayProperties.currencyCode());

        if (bankCode != null && !bankCode.isEmpty()) {
            vnpParams.put("vnp_BankCode", bankCode);
        }

        vnpParams.put("vnp_TxnRef", vnpTxnRef);
        vnpParams.put("vnp_OrderInfo", "Thanh toan don hang " + vnpTxnRef);


        vnpParams.put("vnp_OrderType", orderType);
        vnpParams.put("vnp_Locale", vnPayProperties.locale());

        vnpParams.put("vnp_ReturnUrl", "http://localhost:3000/payment-return");

        vnpParams.put("vnp_IpAddr", vnpIpAddr);

        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        vnpParams.put("vnp_CreateDate", formatter.format(cld.getTime()));
        cld.add(Calendar.MINUTE, 15);
        vnpParams.put("vnp_ExpireDate", formatter.format(cld.getTime()));

        List<String> fieldNames = new ArrayList<>(vnpParams.keySet());
        Collections.sort(fieldNames);

        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();

        for (Iterator<String> itr = fieldNames.iterator(); itr.hasNext(); ) {
            String fieldName = itr.next();
            String fieldValue = vnpParams.get(fieldName);
            if (fieldValue != null && !fieldValue.isEmpty()) {
                hashData.append(fieldName).append('=').append(URLEncoder.encode(fieldValue, StandardCharsets.UTF_8));
                query.append(URLEncoder.encode(fieldName, StandardCharsets.UTF_8))
                        .append('=')
                        .append(URLEncoder.encode(fieldValue, StandardCharsets.UTF_8));
                if (itr.hasNext()) {
                    hashData.append('&');
                    query.append('&');
                }
            }
        }

        String vnpSecureHash = vnPayUtil.hmacSHA512(vnPayProperties.hashSecret(), hashData.toString());
        query.append("&vnp_SecureHash=").append(vnpSecureHash);

        String paymentUrl = vnPayProperties.payUrl() + "?" + query;
        return PaymentResponse.builder().url(paymentUrl).build();
    }

    private void validateOrder(OrderResponseDTO order) {
        if (order == null) {
            throw new AppException(ErrorCode.ORDER_NOT_FOUND);
        }

        if (!order.getStatus().equals(OrderStatus.PENDING_PAYMENT)) {
            throw new AppException(ErrorCode.INVALID_ORDER_STATUS);
        }
        if (!order.getPaymentMethod().equals(OrderMethod.VN_PAY)) {
            throw new AppException(ErrorCode.INVALID_PAYMENT_METHOD);

        }
    }


    @Override
    public boolean notifyOrder(
            String vnpResponseCode,
            String vnpTransactionStatus,
            String orderId,
            String vnpTransactionNo,
            String vnpTransactionDate,
            String vnpAmount) {
        try {
            UUID orderUUID = UUID.fromString(orderId);
            UUID transactionId = UUID.nameUUIDFromBytes(orderId.getBytes(StandardCharsets.UTF_8));

            boolean isSuccess = "00".equals(vnpTransactionStatus);

            OrderStatus newStatus = isSuccess ? OrderStatus.PAYMENT_SUCCESS : OrderStatus.PAYMENT_FAILED;

            orderService.changeOrderStatus(orderUUID, newStatus);

            transactionService.createTransaction(
                    transactionId, orderUUID, vnpResponseCode, vnpTransactionDate, vnpAmount);

            log.info(
                    "VNPay call: orderId={}, status={}, txnNo={}, amount={}",
                    orderId,
                    newStatus,
                    vnpTransactionNo,
                    vnpAmount);

            return isSuccess;
        } catch (Exception e) {
            log.error("notifyOrder error", e);
            return false;
        }
    }
}

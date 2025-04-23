package com.example.mobileapi.util;


import com.example.mobileapi.config.VnPayProperties;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.UriComponentsBuilder;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.Clock;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class VnPayUtil {
    VnPayProperties props;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

    public String buildPaymentUrl(String txnRef, BigDecimal amount, String orderInfo, String clientIp, String baseUrl) {
        LocalDateTime now = LocalDateTime.now();
        Map<String, String> params = new TreeMap<>();
        params.put("vnp_Version", props.version());
        params.put("vnp_Command", props.command());
        params.put("vnp_TmnCode", props.tmnCode());
        params.put("vnp_Amount", amount.multiply(new BigDecimal(100)).toBigInteger().toString());
        params.put("vnp_CurrCode", props.currencyCode());
        params.put("vnp_TxnRef", txnRef);
        params.put("vnp_OrderInfo", orderInfo);
        params.put("vnp_Locale", props.locale());
        params.put("vnp_ReturnUrl", UriComponentsBuilder.fromHttpUrl(baseUrl)
                .path(props.returnUrl())
                .toUriString());
        params.put("vnp_IpAddr", clientIp);
        params.put("vnp_CreateDate", now.format(FORMATTER));
        params.put("vnp_ExpireDate", now.plusMinutes(15).format(FORMATTER));

        String query = params.entrySet().stream()
                .map(e -> e.getKey() + "=" + UriUtils.encode(e.getValue(), StandardCharsets.US_ASCII))
                .collect(Collectors.joining("&"));

        String secureHash = hmacSHA512(props.hashSecret(), query);
        return UriComponentsBuilder.fromHttpUrl(props.payUrl())
                .query(query)
                .queryParam("vnp_SecureHash", secureHash)
                .toUriString();
    }

    public boolean validateReturn(Map<String, String> params) {
        if (params.isEmpty() || !params.containsKey("vnp_SecureHash")) return false;
        TreeMap<String, String> fields = new TreeMap<>(params);
        String receivedHash = fields.remove("vnp_SecureHash");
        String data = fields.entrySet().stream()
                .filter(e -> e.getKey().startsWith("vnp_"))
                .map(e -> e.getKey() + "=" + UriUtils.encode(e.getValue(), StandardCharsets.US_ASCII))
                .collect(Collectors.joining("&"));
        String calculated = hmacSHA512(props.hashSecret(), data);
        return StringUtils.pathEquals(receivedHash, calculated)
                && "00".equals(fields.get("vnp_ResponseCode"));
    }

    public String hmacSHA512(String key, String data) {
        try {
            Mac mac = Mac.getInstance("HmacSHA512");
            mac.init(new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "HmacSHA512"));
            byte[] bytes = mac.doFinal(data.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder(bytes.length * 2);
            for (byte b : bytes) sb.append(String.format("%02x", b & 0xff));
            return sb.toString();
        } catch (Exception e) {
            throw new IllegalStateException("Failed to generate HMACSHA512", e);
        }
    }

    public String getClientIpAddress() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();

        if (requestAttributes instanceof ServletRequestAttributes) {
            HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();

            String ip = request.getHeader("X-Forwarded-For");
            if (ip != null && !ip.isEmpty() && !"unknown".equalsIgnoreCase(ip)) {
                return ip.split(",")[0].trim();
            }

            ip = request.getHeader("Proxy-Client-IP");
            if (ip != null && !ip.isEmpty() && !"unknown".equalsIgnoreCase(ip)) {
                return ip;
            }

            ip = request.getHeader("WL-Proxy-Client-IP");
            if (ip != null && !ip.isEmpty() && !"unknown".equalsIgnoreCase(ip)) {
                return ip;
            }

            return request.getRemoteAddr();
        }

        return "UNKNOWN";
    }
}

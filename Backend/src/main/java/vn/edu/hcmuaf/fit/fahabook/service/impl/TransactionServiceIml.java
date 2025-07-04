package vn.edu.hcmuaf.fit.fahabook.service.impl;

import java.util.UUID;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import vn.edu.hcmuaf.fit.fahabook.dto.response.TransactionResponse;
import vn.edu.hcmuaf.fit.fahabook.service.OrderService;
import vn.edu.hcmuaf.fit.fahabook.service.TransactionService;

@Service
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class TransactionServiceIml implements TransactionService {
    OrderService orderService;

    @Override
    public TransactionResponse createTransaction(
            UUID id, UUID orderId, String responseCode, String transactionDate, String amount) {
        return TransactionResponse.builder()
                .id(id)
                .orders(orderService.getOrder(orderId))
                .responseCode(responseCode)
                .transactionDate(transactionDate)
                .amount(amount)
                .build();
    }
}

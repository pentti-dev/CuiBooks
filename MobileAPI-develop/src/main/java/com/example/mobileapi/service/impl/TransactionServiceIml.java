package com.example.mobileapi.service.impl;

import com.example.mobileapi.dto.response.TransactionResponse;
import com.example.mobileapi.service.OrderService;
import com.example.mobileapi.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class TransactionServiceIml implements TransactionService {
    OrderService orderService;

    @Override
    public TransactionResponse createTransaction(UUID id, UUID orderId, String responseCode, String transactionDate, String amount) {
        return TransactionResponse.builder()
                .id(id)
                .orders(orderService.getOrder(orderId))
                .responseCode(responseCode)
                .transactionDate(transactionDate)
                .amount(amount)
                .build();
    }
}

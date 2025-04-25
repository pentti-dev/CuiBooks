package com.example.mobileapi.service.impl;

import com.example.mobileapi.dto.response.TransactionResponse;
import com.example.mobileapi.service.OrderService;
import com.example.mobileapi.service.TransactionService;
import org.springframework.stereotype.Service;

@Service
public class TransactionServiceIml implements TransactionService {
    OrderService orderService;

    @Override
    public TransactionResponse createTransaction(String id, String orderId, String responseCode, String transactionDate, String amount) {
        return TransactionResponse.builder()
                .id(id)
                .orders(orderService.getOrder(Integer.parseInt(orderId)))
                .responseCode(responseCode)
                .transactionDate(transactionDate)
                .amount(amount)
                .build();
    }
}

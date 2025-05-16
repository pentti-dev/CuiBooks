package com.example.mobileapi.service;

import com.example.mobileapi.dto.response.TransactionResponse;

import java.util.UUID;

public interface TransactionService {

    TransactionResponse createTransaction(UUID id, UUID orderId, String responseCode, String transactionDate, String amount);
}

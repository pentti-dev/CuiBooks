package com.example.mobileapi.service;

import com.example.mobileapi.dto.response.TransactionResponse;
import org.springframework.data.domain.Pageable;

public interface TransactionService {

    TransactionResponse createTransaction(String id, String orderId, String responseCode, String transactionDate, String amount);
}

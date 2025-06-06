package vn.edu.hcmuaf.fit.fahabook.service;

import java.util.UUID;

import vn.edu.hcmuaf.fit.fahabook.dto.response.TransactionResponse;

public interface TransactionService {

    TransactionResponse createTransaction(
            UUID id, UUID orderId, String responseCode, String transactionDate, String amount);
}

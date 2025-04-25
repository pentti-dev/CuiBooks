package com.example.mobileapi.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TransactionResponse {
    String id;
    OrderResponseDTO orders;
    String responseCode;
    String transactionDate;
    String createDate;
    String amount;
}

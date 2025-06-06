package vn.edu.hcmuaf.fit.fahabook.dto.response;

import java.util.UUID;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TransactionResponse {
    UUID id;
    OrderResponseDTO orders;
    String responseCode;
    String transactionDate;
    String createDate;
    String amount;
}

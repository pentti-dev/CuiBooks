package vn.edu.hcmuaf.fit.fahabook.dto.response;

import static lombok.AccessLevel.PRIVATE;

import java.util.UUID;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.extern.jackson.Jacksonized;

@Jacksonized
@Builder
@FieldDefaults(level = PRIVATE)
@Getter
@Setter
public class OrderDetailResponseDTO {
    UUID id;
    UUID orderId;
    ProductResponseDTO productResponseDTO;
    Integer quantity;
}

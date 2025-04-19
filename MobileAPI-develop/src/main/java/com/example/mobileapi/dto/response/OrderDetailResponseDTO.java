package com.example.mobileapi.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.extern.jackson.Jacksonized;

import static lombok.AccessLevel.PRIVATE;

@Jacksonized
@Builder
@FieldDefaults(level = PRIVATE)
@Getter
@Setter
public class OrderDetailResponseDTO {
    Integer id;
    Integer orderId;
    ProductResponseDTO productResponseDTO;
    Integer quantity;
}

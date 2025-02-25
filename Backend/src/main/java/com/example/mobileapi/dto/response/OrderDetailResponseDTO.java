package com.example.mobileapi.dto.response;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@Builder
@Getter
public class OrderDetailResponseDTO {
    private Integer id;
    private Integer orderId;
    private ProductResponseDTO productResponseDTO;
    private Integer quantity;
}

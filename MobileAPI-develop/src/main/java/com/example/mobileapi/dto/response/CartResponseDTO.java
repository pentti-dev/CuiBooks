package com.example.mobileapi.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Builder
@Getter
public class CartResponseDTO {
    private UUID id;
    private UUID customerId;
    private BigDecimal totalPrice;
    private List<CartItemResponseDTO> cartItems;
}

package com.example.mobileapi.dto.request;

import lombok.Data;

@Data
public class OrderDetailRequestDTO {
    private Integer productId;
    private Integer quantity;
}

package com.example.mobileapi.dto.request;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Data
@Getter
@Setter
public class OrderRequestDTO {
    private Integer customerId;
    private Integer totalAmount;
    private String address;
    private String numberPhone;
    @Hidden
    private String status;
    @Hidden
    private String paymentMethod;
    private String receiver;
    private List<OrderDetailRequestDTO> orderDetails;
}


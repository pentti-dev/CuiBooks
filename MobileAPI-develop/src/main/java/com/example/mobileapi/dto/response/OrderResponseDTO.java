package com.example.mobileapi.dto.response;

import com.example.mobileapi.entity.enums.OrderStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class OrderResponseDTO {
    Integer id;
    CustomerResponseDTO customerDTO;
    LocalDateTime orderDate;
    Integer totalAmount;
    String address;
    String numberPhone;
    OrderStatus status;
    String receiver;
    List<OrderDetailResponseDTO> orderDetails;
}

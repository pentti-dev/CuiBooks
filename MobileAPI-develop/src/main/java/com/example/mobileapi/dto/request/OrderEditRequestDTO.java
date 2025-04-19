package com.example.mobileapi.dto.request;

import com.example.mobileapi.entity.enums.OrderStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class OrderEditRequestDTO {
    String fullname;
    String address;
    OrderStatus status;
    String phone;
}

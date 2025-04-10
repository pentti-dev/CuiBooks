package com.example.mobileapi.dto.request;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class CartRequestDTO {
    Integer customerId;

}

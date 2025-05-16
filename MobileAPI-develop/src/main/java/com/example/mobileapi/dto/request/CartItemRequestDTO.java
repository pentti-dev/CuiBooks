package com.example.mobileapi.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CartItemRequestDTO {
    UUID cartId;
    UUID productId;
    int quantity;
}

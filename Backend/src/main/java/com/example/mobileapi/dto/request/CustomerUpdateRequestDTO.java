package com.example.mobileapi.dto.request;

import lombok.Data;
import lombok.Getter;

@Data
public class CustomerUpdateRequestDTO {
    String name;
    String email;
    String phone;
}

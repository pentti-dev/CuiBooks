package com.example.mobileapi.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateAddressDto {
    private String address;
    private String numberPhone;
    private String receiver;
    private String note;
    private Integer customerId;
}

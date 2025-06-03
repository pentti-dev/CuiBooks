package com.example.mobileapi.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreatePaymentRequest {
    private String returnUrl; // <-- để nhận từ body
}

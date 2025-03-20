package com.example.mobileapi.dto.response;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Getter
@Data
@Builder
public class LoginResponse {
    String token;
}

package com.example.mobileapi.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class LoginRequest {
    @NotBlank(message = "MISSING_USERNAME")
    String username;
    @NotBlank(message = "MISSING_PASSWORD")
    String password;
}

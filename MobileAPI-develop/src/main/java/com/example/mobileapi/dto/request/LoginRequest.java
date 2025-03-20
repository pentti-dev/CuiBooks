package com.example.mobileapi.dto.request;

import jakarta.validation.Valid;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.io.Serializable;

@Getter
@Setter
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class LoginRequest {

    String username;
    String password;
}

package com.example.mobileapi.dto.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.io.Serializable;

@Getter
@Setter
public class LoginRequest implements Serializable {
    private String username;
    private String password;
}

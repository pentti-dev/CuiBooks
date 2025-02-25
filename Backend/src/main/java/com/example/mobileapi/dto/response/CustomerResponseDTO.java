package com.example.mobileapi.dto.response;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
@Setter
public class CustomerResponseDTO {
    private int id;

    private String fullname;

    private String username;

    private String email;

    private String phone;

    private boolean role;

    private int cartId;
}

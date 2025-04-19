package com.example.mobileapi.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;


@Getter
@Setter
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class CustomerRequestDTO {

    @NotBlank(message = "MISSING_FULLNAME")
    String fullname;
    @NotBlank(message = "MISSING_USERNAME")
    String username;

    @NotBlank(message = "MISSING_EMAIL")
    @Email(message = "INVALID_EMAIL")
    String email;

    @NotBlank(message = "MISSING_PASSWORD")
    String password;

    @NotBlank(message = "MISSING_PHONE")
    String phone;

}

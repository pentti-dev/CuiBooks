package com.example.mobileapi.dto.request;

import com.example.mobileapi.entity.enums.Role;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
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
    @Pattern(
            // (?=.*[a-z]) 1 chữ thường
            // (?=.*[A-Z])  1 chữ hoa
            // (?=.*\d)   1 chữ số
            // (?=.*[@$!%*?&])
            // .{8,} tối thiểu 8
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&]).{8,}$",
            message = "INVALID_PASSWORD"
    )
    String password;

    @NotBlank(message = "MISSING_PHONE")
    @Pattern(
            regexp = "^(0|\\+84)[0-9]{9,10}$"
            , message = "INVALID_PHONE")
    String phone;
    @Hidden
    Role role;

}

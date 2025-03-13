package com.example.mobileapi.dto.response;

import com.example.mobileapi.model.enums.Role;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;


@Getter
@Builder
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CustomerResponseDTO {
    int id;

    String fullName;

    String username;

    String email;

    String phone;
    @Enumerated(EnumType.STRING)
    Role role;

    int cartId;
}

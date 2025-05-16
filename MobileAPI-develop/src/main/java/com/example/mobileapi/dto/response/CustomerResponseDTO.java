package com.example.mobileapi.dto.response;

import com.example.mobileapi.entity.enums.Role;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.UUID;


@Getter
@Builder
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomerResponseDTO {
    UUID id;

    String fullname;

    String username;

    String email;

    String phone;

    Role role;

    UUID cartId;
}

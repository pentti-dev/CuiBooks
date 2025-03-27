package com.example.mobileapi.dto.response;

import com.example.mobileapi.model.enums.Role;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.*;
import lombok.experimental.FieldDefaults;


@Getter
@Builder
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomerResponseDTO {
    Integer id;

    String fullname;

    String username;

    String email;

    String phone;

    Role role;

    Integer cartId;
}

package com.example.mobileapi.dto.response;

import com.example.mobileapi.model.enums.Role;
import lombok.*;
import lombok.experimental.FieldDefaults;


@Getter
@Builder
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CustomerResponseDTO {
    int id;

    String fullname;

    String username;

    String email;

    String phone;
    Role role;

    int cartId;
}

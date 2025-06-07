package vn.edu.hcmuaf.fit.fahabook.dto.response;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.*;
import lombok.experimental.FieldDefaults;
import vn.edu.hcmuaf.fit.fahabook.entity.enums.Role;

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

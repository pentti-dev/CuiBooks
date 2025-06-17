package vn.edu.hcmuaf.fit.fahabook.dto.base;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import vn.edu.hcmuaf.fit.fahabook.entity.enums.CustomerStatus;
import vn.edu.hcmuaf.fit.fahabook.entity.enums.Role;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CustomerDTO {

    UUID id;


    String fullname;


    String username;


    @Email(message = "INVALID_EMAIL")
    String email;


    @Pattern(
            // (?=.*[a-z]) 1 chữ thường
            // (?=.*[A-Z])  1 chữ hoa
            // (?=.*\d)   1 chữ số
            // (?=.*[@$!%*?&])
            // .{8,} tối thiểu 8
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&]).{8,}$",
            message = "INVALID_PASSWORD")
    String password;


    @Pattern(regexp = "^(0|\\+84)\\d{9,10}$", message = "INVALID_PHONE")
    String phone;

    CustomerStatus status;
    Role role;
}

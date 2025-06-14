package vn.edu.hcmuaf.fit.fahabook.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import vn.edu.hcmuaf.fit.fahabook.annotation.UniqueCustomerField;
import vn.edu.hcmuaf.fit.fahabook.dto.validationgroup.ValidationGroups;
import vn.edu.hcmuaf.fit.fahabook.entity.enums.CustomerStatus;
import vn.edu.hcmuaf.fit.fahabook.entity.enums.Role;

@Getter
@Setter
@Builder
@UniqueCustomerField(groups = {ValidationGroups.Create.class, ValidationGroups.Update.class})
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class CustomerRequestDTO {

    @NotBlank(message = "MISSING_FULLNAME"
            , groups = ValidationGroups.Create.class)
    String fullname;

    @NotBlank(message = "MISSING_USERNAME"
            , groups = ValidationGroups.Create.class)
    String username;

    @NotBlank(message = "MISSING_EMAIL"
            , groups = ValidationGroups.Create.class)
    @Email(message = "INVALID_EMAIL"
            , groups = {ValidationGroups.Create.class, ValidationGroups.Update.class})
    String email;

    @NotBlank(message = "MISSING_PASSWORD"
            , groups = ValidationGroups.Create.class)
    @Pattern(
            // (?=.*[a-z]) 1 chữ thường
            // (?=.*[A-Z])  1 chữ hoa
            // (?=.*\d)   1 chữ số
            // (?=.*[@$!%*?&])
            // .{8,} tối thiểu 8
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&]).{8,}$",
            message = "INVALID_PASSWORD"
            , groups = {ValidationGroups.Create.class, ValidationGroups.Update.class})
    String password;

    @NotBlank(message = "MISSING_PHONE", groups = ValidationGroups.Create.class)
    @Pattern(regexp = "^(0|\\+84)\\d{9,10}$", message = "INVALID_PHONE"
            , groups = {ValidationGroups.Create.class, ValidationGroups.Update.class})
    String phone;

    CustomerStatus status;
    Role role;
}

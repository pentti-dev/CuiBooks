package vn.edu.hcmuaf.fit.fahabook.dto.request.create;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import vn.edu.hcmuaf.fit.fahabook.dto.base.CustomerDTO;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class CreateCustomerDTO extends CustomerDTO {

    @JsonIgnore
    @Override
    public UUID getId() {
        return super.getId();
    }

    @NotBlank(message = "MISSING_FULLNAME")
    @Override
    public String getFullname() {
        return super.getFullname();
    }

    @NotBlank(message = "MISSING_USERNAME")
    @Override
    public String getUsername() {
        return super.getUsername();
    }

    @NotBlank(message = "MISSING_EMAIL")
    @Override
    public String getEmail() {
        return super.getEmail();
    }

    @NotBlank(message = "MISSING_PASSWORD")
    @Override
    public String getPassword() {
        return super.getPassword();
    }

    @NotBlank(message = "MISSING_PHONE")
    @Override
    public String getPhone() {
        return super.getPhone();
    }

}

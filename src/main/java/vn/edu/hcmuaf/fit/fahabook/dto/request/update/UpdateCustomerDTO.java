package vn.edu.hcmuaf.fit.fahabook.dto.request.update;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class UpdateCustomerDTO extends CustomerDTO {
    @JsonIgnore
    @Override
    public UUID getId() {
        return super.getId();
    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return super.getPassword();
    }
}

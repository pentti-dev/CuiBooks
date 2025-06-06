package vn.edu.hcmuaf.fit.fahabook.dto.request.update;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import vn.edu.hcmuaf.fit.fahabook.dto.base.ProductDTO;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
@SuperBuilder
public class ProductUpdateDTO extends ProductDTO {
    @JsonIgnore
    @Override
    public UUID getId() {
        return super.getId();
    }

    String categoryName;
}

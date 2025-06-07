package vn.edu.hcmuaf.fit.fahabook.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import vn.edu.hcmuaf.fit.fahabook.dto.base.ProductDTO;

@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@SuperBuilder
public class ProductResponseDTO extends ProductDTO {
    String categoryName;
}

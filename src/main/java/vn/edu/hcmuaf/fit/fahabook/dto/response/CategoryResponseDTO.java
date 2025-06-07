package vn.edu.hcmuaf.fit.fahabook.dto.response;

import java.util.UUID;

import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Builder
@Getter
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class CategoryResponseDTO {
    UUID id;
    String name;
    String code;
    String description;
}

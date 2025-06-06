package com.example.mobileapi.dto.request.update;

import com.example.mobileapi.dto.base.ProductDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

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

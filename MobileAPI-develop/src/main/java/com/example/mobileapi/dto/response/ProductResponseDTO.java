package com.example.mobileapi.dto.response;

import com.example.mobileapi.entity.enums.BookForm;
import com.example.mobileapi.entity.enums.Language;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.apache.commons.codec.language.bm.Languages;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductResponseDTO {
    UUID id;
    String name;
    String img;
    BigDecimal price;
    String detail;
    String supplier;
    String author;
    Integer publishYear;
    String publisher;
    Language language;
    Integer weight;
    String size;
    Integer pageNumber;
    BookForm form;
    String categoryId;
    String categoryName;
}

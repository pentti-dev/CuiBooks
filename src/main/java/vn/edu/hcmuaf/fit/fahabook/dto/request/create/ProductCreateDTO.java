package vn.edu.hcmuaf.fit.fahabook.dto.request.create;

import java.util.UUID;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import vn.edu.hcmuaf.fit.fahabook.dto.base.ProductDTO;
import vn.edu.hcmuaf.fit.fahabook.entity.enums.BookForm;

@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
@SuperBuilder
public class ProductCreateDTO extends ProductDTO {
    @JsonIgnore
    @Override
    public UUID getId() {
        return super.getId();
    }

    @NotBlank(message = "MISSING_PRODUCT_DETAIL")
    @Override
    public String getDetail() {
        return super.getDetail();
    }

    @NotBlank(message = "MISSING_PRODUCT_SUPPLIER")
    @Override
    public String getSupplier() {
        return super.getSupplier();
    }

    @NotNull(message = "MISSING_PRODUCT_PUBLISH_YEAR")
    @Min(value = 1, message = "MISSING_PRODUCT_PUBLISH_YEAR")
    @Override
    public Integer getPublishYear() {
        return super.getPublishYear();
    }

    @NotBlank(message = "MISSING_PRODUCT_PUBLISHER")
    @Override
    public String getPublisher() {
        return super.getPublisher();
    }

    @NotNull(message = "MISSING_PRODUCT_WEIGHT")
    @Override
    public Integer getWeight() {
        return super.getWeight();
    }

    @NotBlank(message = "MISSING_PRODUCT_SIZE")
    @Override
    public String getSize() {
        return super.getSize();
    }

    @NotNull(message = "MISSING_PRODUCT_PAGE_NUMBER")
    @Override
    public Integer getPageNumber() {
        return super.getPageNumber();
    }

    @NotBlank(message = "MISSING_PRODUCT_FORM")
    @Override
    public BookForm getForm() {
        return super.getForm();
    }

    @NotNull(message = "MISSING_CATEGORY_ID")
    @Override
    public UUID getCategoryId() {
        return super.getCategoryId();
    }
}

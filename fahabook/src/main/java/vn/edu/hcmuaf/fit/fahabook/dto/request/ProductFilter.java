package vn.edu.hcmuaf.fit.fahabook.dto.request;

import java.math.BigDecimal;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.edu.hcmuaf.fit.fahabook.entity.enums.BookForm;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductFilter {
    private String name;
    private UUID categoryId;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    private BookForm form;
    private Double minDiscount;
    private Double maxDiscount;
    private Integer minYear;
    private Integer maxYear;
    private Integer minWeight;
    private Integer maxWeight;
    private String size;
    private Integer minPages;
    private Integer maxPages;
}

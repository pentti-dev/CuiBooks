package vn.edu.hcmuaf.fit.fahabook.dto.response;

import java.util.UUID;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CartItemResponseDTO {
    private UUID id;
    private ProductResponseDTO product;
    private Integer quantity;
}

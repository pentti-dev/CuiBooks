package vn.edu.hcmuaf.fit.fahabook.dto.response;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CartResponseDTO {
    private UUID id;
    private UUID customerId;
    private BigDecimal totalPrice;
    private List<CartItemResponseDTO> cartItems;
}

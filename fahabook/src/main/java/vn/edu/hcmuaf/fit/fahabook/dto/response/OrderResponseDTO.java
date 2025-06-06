package vn.edu.hcmuaf.fit.fahabook.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import vn.edu.hcmuaf.fit.fahabook.entity.enums.OrderStatus;

@Builder
@Getter
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class OrderResponseDTO {
    UUID id;
    CustomerResponseDTO customerDTO;
    LocalDateTime orderDate;
    BigDecimal totalAmount;
    String address;
    String numberPhone;
    OrderStatus status;
    String receiver;
    List<OrderDetailResponseDTO> orderDetails;
    String discountCode;
}

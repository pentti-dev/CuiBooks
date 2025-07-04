package vn.edu.hcmuaf.fit.fahabook.dto.request;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import vn.edu.hcmuaf.fit.fahabook.entity.enums.OrderMethod;
import vn.edu.hcmuaf.fit.fahabook.entity.enums.OrderStatus;

@Getter
@Setter
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class OrderRequestDTO {
    UUID id;
    UUID customerId; // id khách hàng

    @Hidden
    BigDecimal totalAmount; // tổng tiền

    @NotBlank(message = "MISSING_ADDRESS")
    String address; // địa chỉ

    @NotBlank(message = "MISSING_NUMBER_PHONE")
    @Pattern(regexp = "^(0|\\+84)[0-9]{9,10}$", message = "INVALID_PHONE")
    String numberPhone; // số điện thoại

    @Hidden
    OrderStatus status; // trạng thái đơn hàng

    @Hidden
    OrderMethod paymentMethod; // phương thức thanh toán

    @NotBlank(message = "MISSING_RECEIVER")
    String receiver; // người nhận

    @NotNull(message = "MISSING_ORDER_DETAILS")
    @Valid
    List<OrderDetailRequestDTO> orderDetails; // danh sách chi tiết đơn hàng

    String discountCode;
}

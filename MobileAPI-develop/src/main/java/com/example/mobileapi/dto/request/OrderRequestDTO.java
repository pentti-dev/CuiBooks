package com.example.mobileapi.dto.request;

import com.example.mobileapi.entity.enums.OrderMethod;
import com.example.mobileapi.entity.enums.OrderStatus;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Setter
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class OrderRequestDTO {
    Integer customerId;//id khách hàng
    Integer totalAmount;//tổng tiền
    String address;//địa chỉ
    String numberPhone;//số điện thoại
    @Hidden
    OrderStatus status;//trạng thái đơn hàng
    @Hidden
    OrderMethod paymentMethod;//phương thức thanh toán
    String receiver;// người nhận
    List<OrderDetailRequestDTO> orderDetails;// danh sách chi tiết đơn hàng
}


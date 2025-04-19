package com.example.mobileapi.mapper;

import com.example.mobileapi.dto.response.OrderDetailResponseDTO;
import com.example.mobileapi.entity.OrderDetail;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring",
        uses = ProductMapper.class)
public interface OrderDetailMapper {
    OrderDetail toOrderDetail(OrderDetailResponseDTO dto);

    @Mapping(source = "order.id", target = "orderId")
    @Mapping(source = "product", target = "productResponseDTO")
    OrderDetailResponseDTO toOrderDetailResponseDTO(OrderDetail orderDetail);

    List<OrderDetailResponseDTO> toOrderDetailResponseDTOList(List<OrderDetail> orderDetails);
}

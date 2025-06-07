package vn.edu.hcmuaf.fit.fahabook.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import vn.edu.hcmuaf.fit.fahabook.dto.response.OrderDetailResponseDTO;
import vn.edu.hcmuaf.fit.fahabook.entity.OrderDetail;

@Mapper(componentModel = "spring", uses = ProductMapper.class)
public interface OrderDetailMapper {
    OrderDetail toOrderDetail(OrderDetailResponseDTO dto);

    @Mapping(source = "order.id", target = "orderId")
    @Mapping(source = "product", target = "productResponseDTO")
    OrderDetailResponseDTO toOrderDetailResponseDTO(OrderDetail orderDetail);

    List<OrderDetailResponseDTO> toOrderDetailResponseDTOList(List<OrderDetail> orderDetails);
}

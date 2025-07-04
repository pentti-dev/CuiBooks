package vn.edu.hcmuaf.fit.fahabook.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import vn.edu.hcmuaf.fit.fahabook.dto.request.OrderRequestDTO;
import vn.edu.hcmuaf.fit.fahabook.dto.response.OrderResponseDTO;
import vn.edu.hcmuaf.fit.fahabook.entity.Order;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    Order toOrder(OrderRequestDTO dto);

    OrderResponseDTO toOrderResponseDTO(Order entity);

    void updateOrderFromDto(OrderRequestDTO dto, @MappingTarget Order entity);

    List<OrderResponseDTO> toOrderResponseDTOList(List<Order> entities);
}

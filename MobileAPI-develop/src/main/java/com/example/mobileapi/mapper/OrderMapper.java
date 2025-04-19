package com.example.mobileapi.mapper;

import com.example.mobileapi.dto.request.OrderRequestDTO;
import com.example.mobileapi.dto.response.OrderResponseDTO;
import com.example.mobileapi.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    Order toOrder(OrderRequestDTO dto);

    OrderResponseDTO toOrderResponseDTO(Order entity);

    void updateOrderFromDto(OrderRequestDTO dto, @MappingTarget Order entity);
}

package com.example.mobileapi.mapper;

import com.example.mobileapi.dto.request.OrderRequestDTO;
import com.example.mobileapi.dto.response.OrderResponseDTO;
import com.example.mobileapi.model.Order;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    Order toOrder(OrderRequestDTO request);

    OrderResponseDTO toOrderResponseDTO(Order order);
}

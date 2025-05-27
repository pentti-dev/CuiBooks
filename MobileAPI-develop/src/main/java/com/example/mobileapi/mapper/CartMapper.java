package com.example.mobileapi.mapper;

import com.example.mobileapi.dto.request.CartRequestDTO;
import com.example.mobileapi.dto.response.CartResponseDTO;
import com.example.mobileapi.entity.Cart;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CartMapper {

    @Mapping(target = "cartItems", ignore = true)
    @Mapping(target = "customer", ignore = true)
    @Mapping(target = "id", ignore = true)
    Cart toCart(CartRequestDTO dto);

    CartResponseDTO toCartResponseDTO(Cart cart);

}

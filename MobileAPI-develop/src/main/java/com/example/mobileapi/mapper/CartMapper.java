package com.example.mobileapi.mapper;

import com.example.mobileapi.dto.request.CartRequestDTO;
import com.example.mobileapi.dto.response.CartResponseDTO;
import com.example.mobileapi.entity.Cart;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CartMapper {
    Cart toCart(CartRequestDTO dto);

    CartResponseDTO toCartResponseDTO(Cart cart);

}

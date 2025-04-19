package com.example.mobileapi.mapper;

import com.example.mobileapi.dto.request.CartItemRequestDTO;
import com.example.mobileapi.dto.response.CartItemResponseDTO;
import com.example.mobileapi.entity.CartItem;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CartItemMapper {
    CartItem toCartItem(CartItemRequestDTO dto);

    CartItemResponseDTO toCartItemResponseDTO(CartItem cartItem);

    List<CartItemResponseDTO> toCartItemResponseDTOList(List<CartItem> cartItemList);

}

package vn.edu.hcmuaf.fit.fahabook.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import vn.edu.hcmuaf.fit.fahabook.dto.request.CartItemRequestDTO;
import vn.edu.hcmuaf.fit.fahabook.dto.response.CartItemResponseDTO;
import vn.edu.hcmuaf.fit.fahabook.entity.CartItem;

@Mapper(componentModel = "spring")
public interface CartItemMapper {
    CartItem toCartItem(CartItemRequestDTO dto);

    CartItemResponseDTO toCartItemResponseDTO(CartItem cartItem);

    List<CartItemResponseDTO> toCartItemResponseDTOList(List<CartItem> cartItemList);
}

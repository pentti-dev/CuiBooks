package vn.edu.hcmuaf.fit.fahabook.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import vn.edu.hcmuaf.fit.fahabook.dto.request.CartRequestDTO;
import vn.edu.hcmuaf.fit.fahabook.dto.response.CartResponseDTO;
import vn.edu.hcmuaf.fit.fahabook.entity.Cart;

@Mapper(componentModel = "spring")
public interface CartMapper {

    @Mapping(target = "cartItems", ignore = true)
    @Mapping(target = "customer", ignore = true)
    @Mapping(target = "id", ignore = true)
    Cart toCart(CartRequestDTO dto);

    CartResponseDTO toCartResponseDTO(Cart cart);
}

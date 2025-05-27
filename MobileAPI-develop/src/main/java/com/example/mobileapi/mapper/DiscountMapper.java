package com.example.mobileapi.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.mobileapi.dto.DiscountDTO;
import com.example.mobileapi.entity.Discount;

@Mapper(componentModel = "spring")
public interface DiscountMapper {
    @Mapping(target = "id", ignore = true)
    Discount toDiscount(DiscountDTO dto);

    DiscountDTO toDTO(Discount entity);

    List<DiscountDTO> toListDTO(List<Discount> listEntity);

}

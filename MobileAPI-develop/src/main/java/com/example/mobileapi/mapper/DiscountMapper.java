package com.example.mobileapi.mapper;

import com.example.mobileapi.dto.request.create.CreateDiscountDTO;
import com.example.mobileapi.dto.request.update.UpdateDiscountDTO;
import com.example.mobileapi.dto.response.DiscountResponseDTO;
import com.example.mobileapi.entity.Discount;
import org.mapstruct.*;

import java.util.List;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface DiscountMapper {

    @Mapping(target = "id", ignore = true)
    Discount toEntity(CreateDiscountDTO dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    void updateEntityFromDto(UpdateDiscountDTO dto, @MappingTarget Discount entity);

    DiscountResponseDTO toResponseDto(Discount entity);

    List<DiscountResponseDTO> toResponseDtoList(List<Discount> entities);
}

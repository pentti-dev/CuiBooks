package vn.edu.hcmuaf.fit.fahabook.mapper;

import java.util.List;

import org.mapstruct.*;

import vn.edu.hcmuaf.fit.fahabook.dto.request.create.CreateDiscountDTO;
import vn.edu.hcmuaf.fit.fahabook.dto.request.update.UpdateDiscountDTO;
import vn.edu.hcmuaf.fit.fahabook.dto.response.DiscountResponseDTO;
import vn.edu.hcmuaf.fit.fahabook.entity.Discount;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface DiscountMapper {

    @Mapping(target = "id", ignore = true)
    Discount toEntity(CreateDiscountDTO dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    void updateEntityFromDto(UpdateDiscountDTO dto, @MappingTarget Discount entity);

    DiscountResponseDTO toResponseDto(Discount entity);

    List<DiscountResponseDTO> toResponseDtoList(List<Discount> entities);
}

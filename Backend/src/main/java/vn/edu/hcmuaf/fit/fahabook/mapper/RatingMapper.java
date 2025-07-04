package vn.edu.hcmuaf.fit.fahabook.mapper;

import org.mapstruct.Mapper;

import org.mapstruct.Mapping;
import vn.edu.hcmuaf.fit.fahabook.dto.request.RatingRequestDTO;
import vn.edu.hcmuaf.fit.fahabook.dto.response.RatingResponseDTO;
import vn.edu.hcmuaf.fit.fahabook.entity.Rating;

@Mapper(componentModel = "spring")
public interface RatingMapper {
    Rating toEntity(RatingRequestDTO dto);

    RatingRequestDTO toRequestDTO(Rating entity);

    @Mapping(source = "customer.fullname", target = "customerName")
    RatingResponseDTO toResponseDTO(Rating entity);

}

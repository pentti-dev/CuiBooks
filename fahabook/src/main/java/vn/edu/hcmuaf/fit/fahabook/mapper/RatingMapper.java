package vn.edu.hcmuaf.fit.fahabook.mapper;

import org.mapstruct.Mapper;

import vn.edu.hcmuaf.fit.fahabook.dto.request.RatingRequestDTO;
import vn.edu.hcmuaf.fit.fahabook.dto.response.RatingResponseDTO;
import vn.edu.hcmuaf.fit.fahabook.entity.Rating;

@Mapper(componentModel = "spring")
public interface RatingMapper {
    Rating toEntity(RatingRequestDTO dto);

    RatingRequestDTO toRequestDTO(Rating entity);

    RatingResponseDTO toResponseDTO(Rating entity);
}

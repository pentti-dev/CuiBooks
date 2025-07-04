package vn.edu.hcmuaf.fit.fahabook.service;

import java.util.List;
import java.util.UUID;

import vn.edu.hcmuaf.fit.fahabook.dto.request.RatingRequestDTO;
import vn.edu.hcmuaf.fit.fahabook.dto.response.RatingResponseDTO;

public interface RatingService {

    RatingResponseDTO create(RatingRequestDTO dto);

    RatingResponseDTO update(UUID id, RatingRequestDTO dto);

    RatingResponseDTO getById(UUID id);

    void delete(UUID id);

    List<RatingResponseDTO> getAllByProduct(UUID productId);

    Double avgRating(UUID productId);
}

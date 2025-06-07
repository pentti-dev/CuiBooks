package vn.edu.hcmuaf.fit.fahabook.service;

import java.util.UUID;

import vn.edu.hcmuaf.fit.fahabook.dto.request.RatingRequestDTO;
import vn.edu.hcmuaf.fit.fahabook.dto.response.RatingResponseDTO;

public interface RatingService {

    void create(RatingRequestDTO dto);

    RatingResponseDTO update(RatingRequestDTO dto);

    RatingResponseDTO getById(Long id);

    void delete(UUID id);

    void getAllByProduct(UUID productId);
}

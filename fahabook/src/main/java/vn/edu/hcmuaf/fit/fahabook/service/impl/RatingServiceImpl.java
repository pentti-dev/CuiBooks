package vn.edu.hcmuaf.fit.fahabook.service.impl;

import java.util.UUID;

import org.springframework.stereotype.Service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import vn.edu.hcmuaf.fit.fahabook.dto.request.RatingRequestDTO;
import vn.edu.hcmuaf.fit.fahabook.dto.response.RatingResponseDTO;
import vn.edu.hcmuaf.fit.fahabook.mapper.RatingMapper;
import vn.edu.hcmuaf.fit.fahabook.repository.RatingRepository;
import vn.edu.hcmuaf.fit.fahabook.service.RatingService;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
public class RatingServiceImpl implements RatingService {
    RatingRepository ratingRepository;
    RatingMapper ratingMapper;

    @Override
    public void create(RatingRequestDTO dto) {
        // create rating
    }

    @Override
    public RatingResponseDTO update(RatingRequestDTO dto) {
        return null;
    }

    @Override
    public RatingResponseDTO getById(Long id) {
        return null;
    }

    @Override
    public void delete(UUID id) {
        // delete rating

    }

    @Override
    public void getAllByProduct(UUID productId) {
        // Get all ratings for a product
    }
}

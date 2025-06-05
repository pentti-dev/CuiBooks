package com.example.mobileapi.service.impl;

import com.example.mobileapi.dto.request.RatingRequestDTO;
import com.example.mobileapi.dto.response.RatingResponseDTO;
import com.example.mobileapi.mapper.RatingMapper;
import com.example.mobileapi.repository.RatingRepository;
import com.example.mobileapi.service.RatingService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
public class RatingServiceImpl implements RatingService {
    RatingRepository ratingRepository;
    RatingMapper ratingMapper;

    @Override
    public void create(RatingRequestDTO dto) {
        //create rating
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
        //delete rating

    }

    @Override
    public void getAllByProduct(UUID productId) {
// Get all ratings for a product
    }
}

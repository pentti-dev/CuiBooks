package com.example.mobileapi.service;

import com.example.mobileapi.dto.request.RatingRequestDTO;
import com.example.mobileapi.dto.response.RatingResponseDTO;

import java.util.UUID;

public interface RatingService {

    void create(RatingRequestDTO dto);

    RatingResponseDTO update(RatingRequestDTO dto);

    RatingResponseDTO getById(Long id);

    void delete(UUID id);

    void getAllByProduct(UUID productId);
}

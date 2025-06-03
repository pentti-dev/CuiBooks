package com.example.mobileapi.controller;

import com.example.mobileapi.dto.request.RatingRequestDTO;
import com.example.mobileapi.dto.response.ApiResponse;
import com.example.mobileapi.dto.response.RatingResponseDTO;
import com.example.mobileapi.service.RatingService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/rating")
@RequiredArgsConstructor
@Tag(name = "Rating", description = "RatingAPI")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class RatingController {
    RatingService ratingService;

    ApiResponse<Void> create(RatingRequestDTO dto) {
        return ApiResponse
                .success();
    }

    ApiResponse<RatingResponseDTO> update(RatingRequestDTO dto) {
        return null;
    }

    ApiResponse<RatingResponseDTO> getById(Long id) {
        return null;
    }

    ApiResponse<Void> delete(Long id) {
        return ApiResponse.success();
    }

    ApiResponse<List<RatingResponseDTO>> getAllByProduct(UUID productId) {
        return null;

    }
}

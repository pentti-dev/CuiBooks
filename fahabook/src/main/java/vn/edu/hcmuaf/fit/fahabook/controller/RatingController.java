package vn.edu.hcmuaf.fit.fahabook.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import vn.edu.hcmuaf.fit.fahabook.dto.request.RatingRequestDTO;
import vn.edu.hcmuaf.fit.fahabook.dto.response.ApiResponse;
import vn.edu.hcmuaf.fit.fahabook.dto.response.RatingResponseDTO;
import vn.edu.hcmuaf.fit.fahabook.service.RatingService;

@RestController
@RequestMapping("/api/rating")
@RequiredArgsConstructor
@Tag(name = "Rating", description = "RatingAPI")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class RatingController {
    RatingService ratingService;

    ApiResponse<Void> create(RatingRequestDTO dto) {
        return ApiResponse.success();
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

package vn.edu.hcmuaf.fit.fahabook.controller;

import java.util.List;
import java.util.UUID;

import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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
@PreAuthorize("hasRole('USER')")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class RatingController {
    RatingService ratingService;

    @PostMapping
    public ApiResponse<RatingResponseDTO> create(
            @Valid @RequestBody RatingRequestDTO dto) {
        RatingResponseDTO created = ratingService.create(dto);
        return ApiResponse.success(created);
    }

    @PatchMapping("/{id}")
    public ApiResponse<RatingResponseDTO> update(
            @PathVariable("id") UUID id,
            @RequestBody RatingRequestDTO dto) {
        RatingResponseDTO updated = ratingService.update(id, dto);
        return ApiResponse.success(updated);
    }

    @GetMapping("/{id}")
    public ApiResponse<RatingResponseDTO> getById(
            @PathVariable("id") UUID id) {
        RatingResponseDTO rating = ratingService.getById(id);
        return ApiResponse.success(rating);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(
            @PathVariable("id") UUID id) {
        ratingService.delete(id);
        return ApiResponse.success();
    }

    @GetMapping("/product/{productId}")
    public ApiResponse<List<RatingResponseDTO>> getAllByProduct(
            @PathVariable("productId") UUID productId) {
        List<RatingResponseDTO> list = ratingService.getAllByProduct(productId);
        return ApiResponse.success(list);
    }

    @GetMapping("/avg/{productId}")
    public ApiResponse<Double> avgRating(
            @PathVariable("productId") UUID productId) {
        return ApiResponse.success(ratingService.avgRating(productId));
    }
}

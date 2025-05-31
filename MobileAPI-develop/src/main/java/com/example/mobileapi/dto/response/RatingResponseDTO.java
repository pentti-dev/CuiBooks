package com.example.mobileapi.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.Instant;

@Getter
@Setter
@FieldDefaults
        (level = lombok.AccessLevel.PRIVATE)
@Builder
public class RatingResponseDTO {
    String customerName;
    String comment;
    Integer rating;
    Instant createdAt;
}

package vn.edu.hcmuaf.fit.fahabook.dto.response;

import java.time.Instant;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
@Builder
public class RatingResponseDTO {
    String customerName;
    String comment;
    Integer rating;
    Instant createdAt;
}

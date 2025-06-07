package vn.edu.hcmuaf.fit.fahabook.dto.response;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Getter
@Data
@Builder
public class LoginResponse {
    String token;
}

package vn.edu.hcmuaf.fit.fahabook.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreatePaymentRequest {
    private String returnUrl; // <-- để nhận từ body
}

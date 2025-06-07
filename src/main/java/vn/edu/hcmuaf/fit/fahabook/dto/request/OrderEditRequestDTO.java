package vn.edu.hcmuaf.fit.fahabook.dto.request;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import vn.edu.hcmuaf.fit.fahabook.entity.enums.OrderStatus;

@Getter
@Setter
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class OrderEditRequestDTO {
    String fullname;
    String address;
    OrderStatus status;
    String phone;
}

package vn.edu.hcmuaf.fit.fahabook.dto.request;

import java.util.UUID;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateAddressDto {
    String address;
    String numberPhone;
    String receiver;
    String note;
    UUID customerId;
}

package com.example.mobileapi.dto;

import java.util.Date;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DiscountDTO {
    String code;
    Integer percent;
    Date startDate;
    Date endDate;
    Boolean active;

}
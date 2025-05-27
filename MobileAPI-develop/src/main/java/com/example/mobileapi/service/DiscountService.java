package com.example.mobileapi.service;

import com.example.mobileapi.dto.DiscountDTO;

import java.util.List;
import java.util.UUID;

public interface DiscountService {
    boolean checkVailidDIscountCode(String code);

    Integer getPercentDiscount(String code);

    boolean checkDiscountTime(String code);

    DiscountDTO getDiscount(String code);

    List<DiscountDTO> getAllDiscount();

    DiscountDTO create(DiscountDTO discount);

    DiscountDTO update(DiscountDTO discount);

    void delete(UUID id);
}

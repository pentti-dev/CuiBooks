package com.example.mobileapi.service;

import com.example.mobileapi.dto.DiscountDTO;
import com.example.mobileapi.entity.Discount;

import java.util.List;

public interface DiscountService {
    void checkValidDiscount(String code);

    void checkValidDiscount(Discount discount);

    Integer getDiscountPercent(String code);


    DiscountDTO getDiscount(String code);

    List<DiscountDTO> getAllDiscount();

    DiscountDTO create(DiscountDTO discount);

    DiscountDTO update(DiscountDTO discount);

    void delete(String code);
}

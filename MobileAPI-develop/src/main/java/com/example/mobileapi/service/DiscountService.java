package com.example.mobileapi.service;

import com.example.mobileapi.dto.request.create.CreateDiscountDTO;
import com.example.mobileapi.dto.request.update.UpdateDiscountDTO;
import com.example.mobileapi.dto.response.DiscountResponseDTO;
import com.example.mobileapi.entity.Discount;

import java.util.List;

public interface DiscountService {
    void checkValidDiscount(String code);

    void checkValidDiscount(Discount discount);

    Integer getDiscountPercent(String code);


    DiscountResponseDTO getDiscount(String code);

    List<DiscountResponseDTO> getAllDiscount();

    DiscountResponseDTO create(CreateDiscountDTO discount);

    DiscountResponseDTO update(UpdateDiscountDTO discount);

    void delete(String code);
}

package com.example.mobileapi.service;

import com.example.mobileapi.dto.DiscountDTO;

import java.util.List;
import java.util.UUID;

public interface DiscountService {
    public boolean checkVailidDIscountCode(String code);

    public Integer getPercentDiscount(String code);

    public boolean checkDiscountTime(String code);

    public DiscountDTO getDiscount(String code);

    public List<DiscountDTO> getAllDiscount();

    public DiscountDTO create(DiscountDTO discount);

    public DiscountDTO update(DiscountDTO discount);

    public void delete(UUID id);
}

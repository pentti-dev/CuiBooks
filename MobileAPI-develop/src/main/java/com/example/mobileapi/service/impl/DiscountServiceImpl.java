package com.example.mobileapi.service.impl;

import com.example.mobileapi.dto.DiscountDTO;
import com.example.mobileapi.entity.Discount;
import com.example.mobileapi.exception.AppException;
import com.example.mobileapi.exception.ErrorCode;
import com.example.mobileapi.mapper.DiscountMapper;
import com.example.mobileapi.repository.DiscountRepository;
import com.example.mobileapi.service.DiscountService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DiscountServiceImpl implements DiscountService {
    DiscountRepository discountRepository;
    DiscountMapper discountMapper;

    @Override
    public boolean checkVailidDIscountCode(String code) {
        log.warn(code);
        return discountRepository.findByCodeAndActiveIsTrue(code).isPresent();
    }

    @Override
    public Integer getPercentDiscount(String code) {

        return discountRepository.findByCodeAndActiveIsTrue(code)
                .orElseThrow(() -> new AppException(ErrorCode.INVALID_RESET_CODE))
                .getPercent();
    }

    @Override
    public boolean checkDiscountTime(String code) {
        return discountRepository.checkDiscountTime(code).isPresent();
    }

    @Override
    public DiscountDTO getDiscount(String code) {
        return discountMapper.toDTO(discountRepository.findByCodeAndActiveIsTrue(code)
                .orElseThrow(() -> new AppException(ErrorCode.INVALID_RESET_CODE)));
    }

    @Override
    public List<DiscountDTO> getAllDiscount() {
        return discountMapper.toListDTO(discountRepository.getAllByActive(true));
    }

    @Override
    public DiscountDTO create(DiscountDTO discount) {
        return discountMapper.toDTO(discountRepository
                .save(discountMapper.toDiscount(discount)));

    }

    @Override
    public DiscountDTO update(DiscountDTO dto) {
        Discount entity = discountRepository.findByCodeAndActiveIsTrue(dto.getCode())
                .orElseThrow(() -> new AppException(ErrorCode.DISCOUNT_NOT_FOUND));
        entity.setPercent(dto.getPercent());
        entity.setStartDate(dto.getStartDate());
        entity.setEndDate(dto.getEndDate());
        return discountMapper.toDTO(discountRepository.save(entity));

    }

    @Override
    public void delete(UUID id) {
        Discount discount = discountRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.DISCOUNT_NOT_FOUND));
        discount.setActive(false);
        discountRepository.save(discount);

    }
}

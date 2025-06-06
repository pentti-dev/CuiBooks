package vn.edu.hcmuaf.fit.fahabook.service.impl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import vn.edu.hcmuaf.fit.fahabook.dto.request.create.CreateDiscountDTO;
import vn.edu.hcmuaf.fit.fahabook.dto.request.update.UpdateDiscountDTO;
import vn.edu.hcmuaf.fit.fahabook.dto.response.DiscountResponseDTO;
import vn.edu.hcmuaf.fit.fahabook.entity.Discount;
import vn.edu.hcmuaf.fit.fahabook.exception.AppException;
import vn.edu.hcmuaf.fit.fahabook.exception.ErrorCode;
import vn.edu.hcmuaf.fit.fahabook.mapper.DiscountMapper;
import vn.edu.hcmuaf.fit.fahabook.repository.DiscountRepository;
import vn.edu.hcmuaf.fit.fahabook.service.DiscountService;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DiscountServiceImpl implements DiscountService {

    DiscountRepository discountRepository;
    DiscountMapper discountMapper;

    @Override
    public void checkValidDiscount(String code) {
        checkValidDiscount(getDiscountEntity(code));
    }

    @Override
    public void checkValidDiscount(Discount disc) {
        LocalDate nowDate = LocalDate.now();

        if (disc.getEndDate().isBefore(nowDate)) {
            throw new AppException(ErrorCode.EXPIRED_DISCOUNT);
        }

        if (disc.getStartDate().isAfter(nowDate)) {
            throw new AppException(ErrorCode.DISCOUNT_NOT_STARTED);
        }

        if (Boolean.FALSE.equals(disc.getActive())) {
            throw new AppException(ErrorCode.DISCOUNT_INACTIVE);
        }
    }

    @Override
    public Integer getDiscountPercent(String code) {
        Discount discount = discountRepository.findByCode(code).orElse(null);
        if (discount != null) {
            checkValidDiscount(discount);
        }

        return discount != null ? discount.getPercent() : 0;
    }

    @Override
    public DiscountResponseDTO getDiscount(String code) {
        return discountMapper.toResponseDto(getDiscountEntity(code));
    }

    @Override
    public List<DiscountResponseDTO> getAllDiscount() {
        return discountMapper.toResponseDtoList(discountRepository.getAllByActive(true));
    }

    @Override
    @Transactional
    public DiscountResponseDTO create(CreateDiscountDTO discount) {
        if (discountRepository.existsByCode(discount.getCode())) {
            throw new AppException(ErrorCode.DISCOUNT_EXISTED);
        }
        Discount entity = discountMapper.toEntity(discount);
        return discountMapper.toResponseDto(discountRepository.save(entity));
    }

    @Override
    @Transactional
    public DiscountResponseDTO update(UpdateDiscountDTO dto) {
        Discount disc = getDiscountEntity(dto.getCode());
        discountMapper.updateEntityFromDto(dto, disc);

        return discountMapper.toResponseDto(discountRepository.save(disc));
    }

    @Override
    public void delete(String code) {
        Discount discount = getDiscountEntity(code);
        discount.setActive(false);
        discountRepository.save(discount);
    }

    private Discount getDiscountEntity(String code) {
        return discountRepository.findByCode(code).orElseThrow(() -> new AppException(ErrorCode.DISCOUNT_NOT_FOUND));
    }
}

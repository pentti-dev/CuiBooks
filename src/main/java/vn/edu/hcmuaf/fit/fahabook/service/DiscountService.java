package vn.edu.hcmuaf.fit.fahabook.service;

import java.util.List;

import vn.edu.hcmuaf.fit.fahabook.dto.request.create.CreateDiscountDTO;
import vn.edu.hcmuaf.fit.fahabook.dto.request.update.UpdateDiscountDTO;
import vn.edu.hcmuaf.fit.fahabook.dto.response.DiscountResponseDTO;
import vn.edu.hcmuaf.fit.fahabook.entity.Discount;

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

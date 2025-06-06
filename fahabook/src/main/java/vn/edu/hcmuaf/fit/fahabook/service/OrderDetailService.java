package vn.edu.hcmuaf.fit.fahabook.service;

import java.util.List;
import java.util.UUID;

import vn.edu.hcmuaf.fit.fahabook.dto.request.OrderDetailSaveRequest;
import vn.edu.hcmuaf.fit.fahabook.dto.response.OrderDetailResponseDTO;
import vn.edu.hcmuaf.fit.fahabook.exception.AppException;

public interface OrderDetailService {
    OrderDetailResponseDTO saveOrderDetail(OrderDetailSaveRequest requestDTO) throws AppException;

    OrderDetailResponseDTO updateOrderDetail(UUID id, OrderDetailSaveRequest requestDTO) throws AppException;

    void deleteOrderDetail(UUID id);

    OrderDetailResponseDTO findOrderDetailById(UUID id);

    List<OrderDetailResponseDTO> findOrderDetailByOrderId(UUID orderId);

    OrderDetailResponseDTO findOrderDetailByProductId(UUID productId);
}

package com.example.mobileapi.service;

import com.example.mobileapi.dto.request.OrderDetailSaveRequest;
import com.example.mobileapi.dto.response.OrderDetailResponseDTO;
import com.example.mobileapi.exception.AppException;

import java.util.List;
import java.util.UUID;

public interface OrderDetailService {
    OrderDetailResponseDTO saveOrderDetail(OrderDetailSaveRequest requestDTO) throws AppException;

    OrderDetailResponseDTO updateOrderDetail(UUID id, OrderDetailSaveRequest requestDTO) throws AppException;

    void deleteOrderDetail(UUID id);

    OrderDetailResponseDTO findOrderDetailById(UUID id);

    List<OrderDetailResponseDTO> findOrderDetailByOrderId(UUID orderId);

    OrderDetailResponseDTO findOrderDetailByProductId(UUID productId);


}

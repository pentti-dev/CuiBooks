package com.example.mobileapi.service;

import com.example.mobileapi.dto.request.OrderDetailRequestDTO;
import com.example.mobileapi.dto.request.OrderDetailSaveRequest;
import com.example.mobileapi.dto.response.OrderDetailResponseDTO;
import com.example.mobileapi.model.OrderDetail;

import java.util.List;

public interface OrderDetailService {
    OrderDetailResponseDTO saveOrderDetail(OrderDetailSaveRequest requestDTO);

    OrderDetailResponseDTO updateOrderDetail(int id, OrderDetailSaveRequest requestDTO);

    void deleteOrderDetail(int id);

    OrderDetailResponseDTO findOrderDetailById(int id);

    List<OrderDetailResponseDTO> findOrderDetailByOrderId(int orderId);

    OrderDetailResponseDTO findOrderDetailByProductId(int productId);


}

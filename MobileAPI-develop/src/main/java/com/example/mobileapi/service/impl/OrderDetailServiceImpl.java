package com.example.mobileapi.service.impl;

import com.example.mobileapi.dto.request.OrderDetailSaveRequest;
import com.example.mobileapi.dto.response.OrderDetailResponseDTO;
import com.example.mobileapi.dto.response.ProductResponseDTO;
import com.example.mobileapi.entity.Order;
import com.example.mobileapi.entity.OrderDetail;
import com.example.mobileapi.entity.Product;
import com.example.mobileapi.exception.AppException;
import com.example.mobileapi.exception.ErrorCode;
import com.example.mobileapi.mapper.OrderDetailMapper;
import com.example.mobileapi.mapper.ProductMapper;
import com.example.mobileapi.repository.OrderDetailRepository;
import com.example.mobileapi.repository.OrderRepository;
import com.example.mobileapi.repository.ProductRepository;
import com.example.mobileapi.service.OrderDetailService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderDetailServiceImpl implements OrderDetailService {
    OrderDetailRepository orderDetailRepository;
    OrderRepository orderRepository;
    ProductRepository productRepository;
    OrderDetailMapper orderDetailMapper;
    ProductMapper productMapper;


    @Override
    @Transactional
    public OrderDetailResponseDTO saveOrderDetail(OrderDetailSaveRequest requestDTO) throws AppException {
        Order order = orderRepository.findById(
                requestDTO.getOrderId()).orElseThrow(
                () -> new AppException(ErrorCode.ORDER_NOT_FOUND));
        Product product = productRepository.findById(
                requestDTO.getProductId()).orElseThrow(
                () -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));
        orderDetailRepository.save(OrderDetail.builder()
                .order(order)
                .product(product)
                .quantity(requestDTO.getQuantity())
                .build());

        return OrderDetailResponseDTO.builder()
                .orderId(order.getId())
                .productResponseDTO(productMapper.toProductResponseDTO(product))
                .quantity(requestDTO.getQuantity())
                .build();
    }

    @Override
    @Transactional
    public OrderDetailResponseDTO updateOrderDetail(int id, OrderDetailSaveRequest requestDTO) throws AppException {
        OrderDetail orderDetail = orderDetailRepository.findById(id)
                .orElseThrow(
                        () -> new AppException(ErrorCode.ORDER_DETAIL_NOT_FOUND));

        Order order = orderRepository.findById(
                        requestDTO.getOrderId())
                .orElseThrow(
                        () -> new AppException(ErrorCode.ORDER_NOT_FOUND));
        Product product = productRepository.findById(
                        requestDTO.getProductId())

                .orElseThrow(
                        () -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));

        orderDetail.setOrder(order);
        orderDetail.setProduct(product);
        orderDetail.setQuantity(requestDTO.getQuantity());

        OrderDetail saved = orderDetailRepository.save(orderDetail);

        return orderDetailMapper.toOrderDetailResponseDTO(saved);
    }


    @Override
    public void deleteOrderDetail(int id) {
        orderDetailRepository.deleteById(id);
    }

    @Override
    public OrderDetailResponseDTO findOrderDetailById(int id) {
        OrderDetail orderDetail = orderDetailRepository.findById(id).orElse(null);
        return orderDetailMapper.toOrderDetailResponseDTO(orderDetail);
    }

    @Override
    public List<OrderDetailResponseDTO> findOrderDetailByOrderId(int orderId) {

        List<OrderDetail> orderDetails = orderDetailRepository.findOrderByOrderId(orderId);

        return orderDetailMapper.toOrderDetailResponseDTOList(orderDetails);
    }


    @Override
    public OrderDetailResponseDTO findOrderDetailByProductId(int productId) {
        OrderDetail orderDetail = orderDetailRepository.findOrderByProductId(productId);
        return orderDetailMapper.toOrderDetailResponseDTO(orderDetail);
    }
}

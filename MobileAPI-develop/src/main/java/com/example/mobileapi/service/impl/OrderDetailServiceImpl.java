package com.example.mobileapi.service.impl;

import com.example.mobileapi.dto.request.OrderDetailRequestDTO;
import com.example.mobileapi.dto.request.OrderDetailSaveRequest;
import com.example.mobileapi.dto.response.OrderDetailResponseDTO;
import com.example.mobileapi.dto.response.ProductResponseDTO;
import com.example.mobileapi.model.Order;
import com.example.mobileapi.model.OrderDetail;
import com.example.mobileapi.model.Product;
import com.example.mobileapi.repository.OrderDetailRepository;
import com.example.mobileapi.repository.OrderRepository;
import com.example.mobileapi.repository.ProductRepository;
import com.example.mobileapi.service.OrderDetailService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class OrderDetailServiceImpl implements OrderDetailService {
    private final OrderDetailRepository orderDetailRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;


    @Override
    public OrderDetailResponseDTO saveOrderDetail(OrderDetailSaveRequest requestDTO) {
        Order order = orderRepository.findById(requestDTO.getOrderId()).orElse(null);
        Product product = productRepository.findById(requestDTO.getProductId()).orElse(null);
        orderDetailRepository.save(OrderDetail.builder()
                .order(order)
                .product(product)
                .quantity(requestDTO.getQuantity())
                .build());

        return OrderDetailResponseDTO.builder()
                .orderId(order.getId())
                .productResponseDTO(ProductResponseDTO.builder()
                        .id(product.getId())
                        .name(product.getName())
                        .img(product.getImg())
                        .categoryName(product.getCategory().getName())
                        .price(product.getPrice())
                        .build())
                .quantity(requestDTO.getQuantity())
                .build();
    }

    @Override
    public OrderDetailResponseDTO updateOrderDetail(int id, OrderDetailSaveRequest requestDTO) {
        OrderDetail orderDetail = orderDetailRepository.findById(id).orElse(null);
        Order order = orderRepository.findById(requestDTO.getOrderId()).orElse(null);
        Product product = productRepository.findById(requestDTO.getProductId()).orElse(null);
        orderDetail.setOrder(order);
        orderDetail.setProduct(product);
        orderDetail.setQuantity(requestDTO.getQuantity());
        return OrderDetailResponseDTO.builder()
                .orderId(order.getId())
                .productResponseDTO(ProductResponseDTO.builder()
                        .id(product.getId())
                        .name(product.getName())
                        .img(product.getImg())
                        .categoryName(product.getCategory().getName())
                        .price(product.getPrice())
                        .build())                .quantity(requestDTO.getQuantity())
                .build();
    }

    @Override
    public void deleteOrderDetail(int id) {
        orderDetailRepository.deleteById(id);
    }

    @Override
    public OrderDetailResponseDTO findOrderDetailById(int id) {
        OrderDetail orderDetail = orderDetailRepository.findById(id).orElse(null);
        Product product = productRepository.findById(orderDetail.getProduct().getId()).orElse(null);
        return OrderDetailResponseDTO.builder()
                .productResponseDTO(ProductResponseDTO.builder()
                        .id(product.getId())
                        .name(product.getName())
                        .img(product.getImg())
                        .categoryName(product.getCategory().getName())
                        .price(product.getPrice())
                        .build())                .orderId(orderDetail.getOrder().getId())
                .quantity(orderDetail.getQuantity())
                .build();
    }

    @Override
    public List<OrderDetailResponseDTO> findOrderDetailByOrderId(int orderId) {

        List<OrderDetail> orderDetails = orderDetailRepository.findOrderByOrderId(orderId);

        return orderDetails.stream()
                .map(orderDetail -> OrderDetailResponseDTO.builder()
                        .orderId(orderDetail.getOrder().getId())
                        .productResponseDTO(ProductResponseDTO.builder()
                                .id(orderDetail.getProduct().getId())
                                .price(orderDetail.getProduct().getPrice())
                                .categoryName(orderDetail.getProduct().getCategory().getName())
                                .name(orderDetail.getProduct().getName())
                                .img(orderDetail.getProduct().getImg())
                                .build())
                        .quantity(orderDetail.getQuantity())
                        .build())
                .collect(Collectors.toList());
    }


    @Override
    public OrderDetailResponseDTO findOrderDetailByProductId(int productId) {
        OrderDetail orderDetail = orderDetailRepository.findOrderByProductId(productId);
        return OrderDetailResponseDTO.builder()
                .orderId(orderDetail.getOrder().getId())
                .productResponseDTO(ProductResponseDTO.builder()
                        .id(orderDetail.getProduct().getId())
                        .price(orderDetail.getProduct().getPrice())
                        .categoryName(orderDetail.getProduct().getCategory().getName())
                        .name(orderDetail.getProduct().getName())
                        .img(orderDetail.getProduct().getImg())
                        .build())                .quantity(orderDetail.getQuantity())
                .build();
    }
}

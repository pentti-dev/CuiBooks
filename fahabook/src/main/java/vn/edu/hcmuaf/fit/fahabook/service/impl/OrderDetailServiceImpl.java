package vn.edu.hcmuaf.fit.fahabook.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import vn.edu.hcmuaf.fit.fahabook.dto.request.OrderDetailSaveRequest;
import vn.edu.hcmuaf.fit.fahabook.dto.response.OrderDetailResponseDTO;
import vn.edu.hcmuaf.fit.fahabook.entity.Order;
import vn.edu.hcmuaf.fit.fahabook.entity.OrderDetail;
import vn.edu.hcmuaf.fit.fahabook.entity.Product;
import vn.edu.hcmuaf.fit.fahabook.exception.AppException;
import vn.edu.hcmuaf.fit.fahabook.exception.ErrorCode;
import vn.edu.hcmuaf.fit.fahabook.mapper.OrderDetailMapper;
import vn.edu.hcmuaf.fit.fahabook.mapper.ProductMapper;
import vn.edu.hcmuaf.fit.fahabook.repository.OrderDetailRepository;
import vn.edu.hcmuaf.fit.fahabook.repository.OrderRepository;
import vn.edu.hcmuaf.fit.fahabook.repository.ProductRepository;
import vn.edu.hcmuaf.fit.fahabook.service.OrderDetailService;

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
        Order order = orderRepository
                .findById(requestDTO.getOrderId())
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));
        Product product = productRepository
                .findById(requestDTO.getProductId())
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));
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
    public OrderDetailResponseDTO updateOrderDetail(UUID id, OrderDetailSaveRequest requestDTO) throws AppException {
        OrderDetail orderDetail = orderDetailRepository
                .findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_DETAIL_NOT_FOUND));

        Order order = orderRepository
                .findById(requestDTO.getOrderId())
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));
        Product product = productRepository
                .findById(requestDTO.getProductId())
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));

        orderDetail.setOrder(order);
        orderDetail.setProduct(product);
        orderDetail.setQuantity(requestDTO.getQuantity());

        OrderDetail saved = orderDetailRepository.save(orderDetail);

        return orderDetailMapper.toOrderDetailResponseDTO(saved);
    }

    @Override
    public void deleteOrderDetail(UUID id) {
        orderDetailRepository.deleteById(id);
    }

    @Override
    public OrderDetailResponseDTO findOrderDetailById(UUID id) {
        OrderDetail orderDetail = orderDetailRepository.findById(id).orElse(null);
        return orderDetailMapper.toOrderDetailResponseDTO(orderDetail);
    }

    @Override
    public List<OrderDetailResponseDTO> findOrderDetailByOrderId(UUID orderId) {

        List<OrderDetail> orderDetails = orderDetailRepository.findOrderByOrderId(orderId);

        return orderDetailMapper.toOrderDetailResponseDTOList(orderDetails);
    }

    @Override
    public OrderDetailResponseDTO findOrderDetailByProductId(UUID productId) {
        OrderDetail orderDetail = orderDetailRepository.findOrderByProductId(productId);
        return orderDetailMapper.toOrderDetailResponseDTO(orderDetail);
    }
}

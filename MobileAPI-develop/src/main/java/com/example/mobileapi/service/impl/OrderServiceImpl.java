package com.example.mobileapi.service.impl;

import com.example.mobileapi.entity.enums.OrderMethod;
import com.example.mobileapi.entity.enums.OrderStatus;
import com.example.mobileapi.dto.request.OrderEditRequestDTO;
import com.example.mobileapi.dto.request.OrderRequestDTO;
import com.example.mobileapi.dto.request.OrderDetailRequestDTO;
import com.example.mobileapi.dto.response.MonthlyRevenueResponse;
import com.example.mobileapi.dto.response.OrderResponseDTO;
import com.example.mobileapi.dto.response.OrderDetailResponseDTO;
import com.example.mobileapi.exception.AppException;
import com.example.mobileapi.exception.ErrorCode;
import com.example.mobileapi.entity.Order;
import com.example.mobileapi.entity.OrderDetail;
import com.example.mobileapi.mapper.OrderMapper;
import com.example.mobileapi.mapper.ProductMapper;
import com.example.mobileapi.repository.OrderDetailRepository;
import com.example.mobileapi.repository.OrderRepository;
import com.example.mobileapi.repository.CustomerRepository;
import com.example.mobileapi.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = lombok.AccessLevel.PRIVATE)
public class OrderServiceImpl implements OrderService {
    OrderRepository orderRepository;
    CustomerRepository customerRepository;
    CustomerServiceImpl customerServiceImpl;
    ProductServiceImpl productService;
    ProductMapper productMapper;
    OrderMapper orderMapper;
    DiscountServiceImpl discountService;
    OrderDetailRepository orderDetailRepository;

    @Override
    @PostAuthorize("@customerServiceImpl.getCustomerIdByUsername(authentication.name) == #dto.customerId")
    @PreAuthorize("hasRole('USER')")
    @Transactional
    public UUID saveOrder(OrderRequestDTO dto) throws AppException {
        BigDecimal amount = calcTotalAmount(
                dto.getOrderDetails(),
                discountService.getDiscountPercent(dto.getDiscountCode())
        );

        Order order = Order.builder()
                .customer(
                        customerRepository.findById(dto.getCustomerId())
                                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND))
                )
                .orderDate(LocalDateTime.now())
                .totalAmount(amount)
                .address(dto.getAddress())
                .numberPhone(dto.getNumberPhone())
                .receiver(dto.getReceiver())
                .paymentMethod(dto.getPaymentMethod())
                .status(determineDefaultStatus(dto.getPaymentMethod()))
                .build();
        //Luu đon hàng
        Order savedOrder = orderRepository.save(order);
//Thêm detail khi đã có orderId
        List<OrderDetail> details = dto.getOrderDetails().stream()
                .map(req -> {
                    OrderDetail od = convertToOrderDetailEntity(req);
                    od.setOrder(savedOrder);
                    return od;
                })
                .toList();

        orderDetailRepository.saveAll(details);

        return savedOrder.getId();
    }

    // Tính toán tổng tiền đơn hàng từ chi tiết đơn hàng và mã giảm giá
    @Override
    public BigDecimal calcTotalAmount(List<OrderDetailRequestDTO> orderDetails, Integer discountPercent) {
        // tính tiền tổng
        BigDecimal totalAmount = orderDetails.stream()
                .map(detail -> {
                    BigDecimal price = productService.getPriceById(detail.getProductId());
                    return price.multiply(BigDecimal.valueOf(detail.getQuantity()));
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        // tính toán neeus có mã giảm giá
        if (discountPercent != null && discountPercent > 0) {
            BigDecimal discountAmount = totalAmount
                    .multiply(BigDecimal.valueOf(discountPercent))
                    .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
            totalAmount = totalAmount.subtract(discountAmount);
        }
        return totalAmount;
    }


    public OrderStatus determineDefaultStatus(OrderMethod method) {
        return switch (method) {
            case COD -> OrderStatus.PENDING;
            case VN_PAY, MOMO, ZALO_PAY -> OrderStatus.PENDING_PAYMENT;
            default -> OrderStatus.PENDING;
        };
    }

    @Override
    public OrderResponseDTO getOrder(UUID orderId) {
        Order order = getOrderById(orderId);
        if (order == null) {
            return null;
        }

        return convertToOrderResponseDTO(order);
    }

    @Override
    public BigDecimal getPriceByOrderId(UUID orderId) {
        return orderRepository.findTotalAmountByOrderId(orderId);
    }

    @Override
    public void deleteOrder(UUID orderId) {
        orderRepository.deleteById(orderId);
    }

    @Override
    public void updateOrder(UUID id, OrderRequestDTO orderRequestDTO) {
        Order order = getOrderById(id);
        if (order != null) {
            order.setTotalAmount(orderRequestDTO.getTotalAmount());
            order.setAddress(orderRequestDTO.getAddress());
            order.setNumberPhone(orderRequestDTO.getNumberPhone());
            order.setStatus(orderRequestDTO.getStatus());
            order.setOrderDetails(orderRequestDTO.getOrderDetails().stream()
                    .map(this::convertToOrderDetailEntity)
                    .collect(Collectors.toCollection(ArrayList::new)));
            order.getOrderDetails().forEach(orderDetail -> orderDetail.setOrder(order));
            orderRepository.save(order);
        }
    }

    @Override
    @PreAuthorize("@customerServiceImpl.getCustomerIdByUsername(authentication.name) == #customerId")
    public List<OrderResponseDTO> getOrderByCustomerId(UUID customerId) {
        return getOrdersByCustomerId(customerId);
    }

    @Override
    public List<OrderResponseDTO> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        log.warn("List of orders: {}", orders);
        if (orders.isEmpty()) {
            return Collections.emptyList(); // Return an empty list if no orders found
        }

        return orders.stream()
                .map(this::convertToOrderResponseDTO)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public void editOrder(UUID id, OrderEditRequestDTO orderEditRequestDTO) throws AppException {
        Order order = orderRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));
        order.setAddress(orderEditRequestDTO.getAddress());
        order.setNumberPhone(orderEditRequestDTO.getPhone());
        order.setStatus(orderEditRequestDTO.getStatus());
        order.setReceiver(orderEditRequestDTO.getFullname());
        orderRepository.save(order);
    }

    @Override
    public List<MonthlyRevenueResponse> getMonthlyRevenue() {
        return orderRepository.getMonthlyRevenue().stream()
                .map(p -> MonthlyRevenueResponse.builder()
                        .month(p.getMonth())
                        .revenue(p.getRevenue() != null ? p.getRevenue() : BigDecimal.ZERO)
                        .build())
                .toList();
    }

    @Override
    public List<OrderResponseDTO> getOrdersByStatus(OrderStatus status) {
        List<Order> orders = orderRepository.findByStatus(status);
        if (orders.isEmpty()) {
            return Collections.emptyList();
        }
        return orderMapper.toOrderResponseDTOList(orders);
    }

    @Override
    public void changeOrderStatus(UUID orderId, OrderStatus status) throws AppException {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));
        order.setStatus(status);
        orderRepository.save(order);
    }

    @Override
    @PreAuthorize("@customerServiceImpl.getCustomerIdByUsername(authentication.name) == #customerId")
    public List<OrderResponseDTO> getOrdersByStatusAndCustomerId(OrderStatus status, UUID customerId) {
        List<Order> orderResponseDTO = orderRepository.findByStatusAndCustomer_Id(status, customerId);
        if (orderResponseDTO.isEmpty()) {
            return Collections.emptyList();
        }
        return orderResponseDTO.stream()
                .map(this::convertToOrderResponseDTO)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public boolean existById(UUID orderId) {
        return orderRepository.existsById(orderId);
    }

    public List<OrderResponseDTO> getOrdersByCustomerId(UUID customerId) {
        List<Order> orders = orderRepository.findByCustomerId(customerId);
        if (orders.isEmpty()) {
            return Collections.emptyList(); // Return an empty list if no orders found
        }

        return orders.stream()
                .map(this::convertToOrderResponseDTO)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    private Order getOrderById(UUID orderId) {
        return orderRepository.findById(orderId).orElse(null);
    }

    private OrderResponseDTO convertToOrderResponseDTO(Order order) {
        List<OrderDetailResponseDTO> orderDetailDTOs = order.getOrderDetails().stream()
                .map(this::convertToOrderDetailResponseDTO)
                .collect(Collectors.toCollection(ArrayList::new));

        return OrderResponseDTO.builder()
                .id(order.getId())
                .customerDTO(customerServiceImpl.getCustomer(order.getCustomer().getId()))
                .orderDate(order.getOrderDate())
                .totalAmount(order.getTotalAmount()) // Ensure this matches your field in OrderResponseDTO
                .address(order.getAddress())
                .numberPhone(order.getNumberPhone())
                .status(order.getStatus())
                .receiver(order.getReceiver())
                .orderDetails(orderDetailDTOs)
                .build();
    }

    private OrderDetailResponseDTO convertToOrderDetailResponseDTO(OrderDetail orderDetail) {
        return OrderDetailResponseDTO.builder()
                .id(orderDetail.getId())
                .orderId(orderDetail.getOrder().getId())
                .productResponseDTO(productMapper.toProductResponseDTO(orderDetail.getProduct()))
                .quantity(orderDetail.getQuantity())
                .build();
    }

    private OrderDetail convertToOrderDetailEntity(OrderDetailRequestDTO dto) {
        return OrderDetail.builder()
                .product(productService.getById(dto.getProductId()))
                .quantity(dto.getQuantity())
                .build();
    }
}

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
import com.example.mobileapi.repository.OrderRepository;
import com.example.mobileapi.repository.CustomerRepository;
import com.example.mobileapi.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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
    private final ProductMapper productMapper;
    private final OrderMapper orderMapper;

    @Override
    @PreAuthorize("@customerServiceImpl.getCustomerIdByUsername(authentication.name) == #orderRequestDTO.customerId")
    public UUID saveOrder(OrderRequestDTO orderRequestDTO) throws AppException {

        Order order = Order.builder()
                .customer(customerRepository.findById(orderRequestDTO.getCustomerId()).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND)))
                .orderDate(LocalDateTime.now())
                .totalAmount(orderRequestDTO.getTotalAmount())
                .address(orderRequestDTO.getAddress())
                .numberPhone(orderRequestDTO.getNumberPhone())
                .receiver(orderRequestDTO.getReceiver())
                .status(determineDefaultStatus(orderRequestDTO.getPaymentMethod()))
                .paymentMethod(orderRequestDTO.getPaymentMethod())
                .orderDetails(orderRequestDTO.getOrderDetails().stream()
                        .map(this::convertToOrderDetailEntity)
                        .collect(Collectors.toCollection(ArrayList::new)))
                .build();

        order.getOrderDetails().forEach(orderDetail -> orderDetail.setOrder(order));

        return orderRepository.save(order).getId();
    }

    public OrderStatus determineDefaultStatus(OrderMethod method) {
        switch (method) {
            case COD:
                return OrderStatus.PENDING;
            case VN_PAY,
                 MOMO,
                 ZALO_PAY:
                return OrderStatus.PENDING_PAYMENT;
            default:
                return OrderStatus.PENDING;
        }
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
        List<Object[]> monthlyData = orderRepository.getMonthlyRevenue();
        List<MonthlyRevenueResponse> responseList = new ArrayList<>();

        for (Object[] row : monthlyData) {
            int month = (int) row[0];
            BigDecimal value = (BigDecimal) row[1];
            BigDecimal revenue = value != null ? value : BigDecimal.ZERO;
            responseList.add(MonthlyRevenueResponse.builder().month(month).revenue(revenue).build());
        }

        return responseList;
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

package com.example.mobileapi.service.impl;

import com.example.mobileapi.dto.request.OrderEditRequestDTO;
import com.example.mobileapi.dto.request.OrderRequestDTO;
import com.example.mobileapi.dto.request.OrderDetailRequestDTO;
import com.example.mobileapi.dto.response.MonthlyRevenueResponse;
import com.example.mobileapi.dto.response.OrderResponseDTO;
import com.example.mobileapi.dto.response.OrderDetailResponseDTO;
import com.example.mobileapi.model.Order;
import com.example.mobileapi.model.OrderDetail;
import com.example.mobileapi.repository.OrderRepository;
import com.example.mobileapi.repository.CustomerRepository;
import com.example.mobileapi.service.CustomerService;
import com.example.mobileapi.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final CustomerServiceImpl customerService;
    private final ProductServiceImpl productService;

    @Override
    public int saveOrder(OrderRequestDTO orderRequestDTO) {
        Order order = Order.builder()
                .customer(customerRepository.findById(orderRequestDTO.getCustomerId()).orElse(null))
                .orderDate(LocalDateTime.now())
                .totalAmount(orderRequestDTO.getTotalAmount())
                .address(orderRequestDTO.getAddress())
                .numberPhone(orderRequestDTO.getNumberPhone())
                .receiver(orderRequestDTO.getReceiver())
                .status(orderRequestDTO.getStatus())
                .paymentMethod(orderRequestDTO.getPaymentMethod())
                .orderDetails(orderRequestDTO.getOrderDetails().stream()
                        .map(this::convertToOrderDetailEntity)
                        .collect(Collectors.toList()))
                .build();

        order.getOrderDetails().forEach(orderDetail -> orderDetail.setOrder(order));

        return orderRepository.save(order).getId();
    }

    @Override
    public OrderResponseDTO getOrder(int orderId) {
        Order order = getOrderById(orderId);
        if (order == null) {
            return null;
        }

        return convertToOrderResponseDTO(order);
    }

    @Override
    public void deleteOrder(int orderId) {
        orderRepository.deleteById(orderId);
    }

    @Override
    public void updateOrder(int id, OrderRequestDTO orderRequestDTO) {
        Order order = getOrderById(id);
        if (order != null) {
            order.setTotalAmount(orderRequestDTO.getTotalAmount());
            order.setAddress(orderRequestDTO.getAddress());
            order.setNumberPhone(orderRequestDTO.getNumberPhone());
            order.setStatus(orderRequestDTO.getStatus());
            order.setOrderDetails(orderRequestDTO.getOrderDetails().stream()
                    .map(this::convertToOrderDetailEntity)
                    .collect(Collectors.toList()));
            order.getOrderDetails().forEach(orderDetail -> orderDetail.setOrder(order));
            orderRepository.save(order);
        }
    }

    @Override
    public List<OrderResponseDTO> getOrderByCustomerId(int customerId) {
        return getOrdersByCustomerId(customerId);
    }

    @Override
    public List<OrderResponseDTO> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        if (orders.isEmpty()) {
            return Collections.emptyList(); // Return an empty list if no orders found
        }

        return orders.stream()
                .map(this::convertToOrderResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void editOrder(int id, OrderEditRequestDTO orderEditRequestDTO) {
        Order order = orderRepository.findById(id).orElse(null);
        if (order != null) {
            order.setAddress(orderEditRequestDTO.getAddress());
            order.setNumberPhone(orderEditRequestDTO.getPhone());
            order.setStatus(orderEditRequestDTO.getStatus());
            order.setReceiver(orderEditRequestDTO.getFullname());
            orderRepository.save(order);
        }
    }

    @Override
    public List<MonthlyRevenueResponse> getMonthlyRevenue() {
        List<Object[]> monthlyData = orderRepository.getMonthlyRevenue();
        List<MonthlyRevenueResponse> responseList = new ArrayList<>();

        for (Object[] row : monthlyData) {
            int month = (int) row[0];
            BigDecimal value = (BigDecimal) row[1];
            long revenue = value.longValueExact();
            responseList.add(new MonthlyRevenueResponse(month, revenue));
        }

        return responseList;
    }

    @Override
    public List<OrderResponseDTO> getOrdersByStatus(String status) {
        List<Order> orders = orderRepository.findByStatus(status);
        if (orders.isEmpty()) {
            return Collections.emptyList();
        }
        return orders.stream()
                .map(this::convertToOrderResponseDTO)
                .collect(Collectors.toList());
    }


    @Override
    public void changeOrderStatus(int orderId, String status) {
        Order order = orderRepository.findById(orderId).orElse(null);
        if (order != null) {
            order.setStatus(status);
        }
        orderRepository.save(order);
    }

    @Override
    public List<OrderResponseDTO> getOrdersByStatusAndCustomerId(String status, int customerId) {
        List<Order> orderResponseDTO = orderRepository.findByStatusAndCustomerId(status, customerId);
        if (orderResponseDTO.isEmpty()) {
            return Collections.emptyList();
        }
        return orderResponseDTO.stream()
                .map(this::convertToOrderResponseDTO)
                .collect(Collectors.toList());
    }


    public List<OrderResponseDTO> getOrdersByCustomerId(int customerId) {
        List<Order> orders = orderRepository.findByCustomerId(customerId);
        if (orders.isEmpty()) {
            return Collections.emptyList(); // Return an empty list if no orders found
        }

        return orders.stream()
                .map(this::convertToOrderResponseDTO)
                .collect(Collectors.toList());
    }

    private Order getOrderById(int orderId) {
        return orderRepository.findById(orderId).orElse(null);
    }

    private OrderResponseDTO convertToOrderResponseDTO(Order order) {
        List<OrderDetailResponseDTO> orderDetailDTOs = order.getOrderDetails().stream()
                .map(this::convertToOrderDetailResponseDTO)
                .collect(Collectors.toList());

        return OrderResponseDTO.builder()
                .id(order.getId())
                .customerDTO(customerService.getCustomer(order.getCustomer().getId()))
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
                .productResponseDTO(productService.getProductById(orderDetail.getProduct().getId()))
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

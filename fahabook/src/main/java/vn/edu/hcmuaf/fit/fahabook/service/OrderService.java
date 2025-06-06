package vn.edu.hcmuaf.fit.fahabook.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import vn.edu.hcmuaf.fit.fahabook.dto.request.OrderDetailRequestDTO;
import vn.edu.hcmuaf.fit.fahabook.dto.request.OrderEditRequestDTO;
import vn.edu.hcmuaf.fit.fahabook.dto.request.OrderRequestDTO;
import vn.edu.hcmuaf.fit.fahabook.dto.response.OrderResponseDTO;
import vn.edu.hcmuaf.fit.fahabook.dto.response.RevenueResponse;
import vn.edu.hcmuaf.fit.fahabook.entity.enums.OrderStatus;
import vn.edu.hcmuaf.fit.fahabook.exception.AppException;

public interface OrderService {
    UUID saveOrder(OrderRequestDTO orderRequestDTO) throws AppException;

    // Tính toán tổng tiền đơn hàng từ chi tiết đơn hàng và mã giảm giá
    BigDecimal calcTotalAmount(List<OrderDetailRequestDTO> orderDetails, Integer discountPercent);

    OrderResponseDTO getOrder(UUID orderId);

    BigDecimal getPriceByOrderId(UUID orderId);

    void deleteOrder(UUID orderId);

    void updateOrder(UUID id, OrderRequestDTO orderRequestDTO);

    List<OrderResponseDTO> getOrderByCustomerId(UUID customerId);

    List<OrderResponseDTO> getAllOrders();

    void editOrder(UUID id, OrderEditRequestDTO orderEditRequestDTO) throws AppException;

    List<RevenueResponse> getMonthlyRevenue();

    RevenueResponse getRevenueByMonth(int month, int year);

    RevenueResponse getRevenueByYear(int year);

    RevenueResponse getRevenueByDate(LocalDate date);

    List<OrderResponseDTO> getOrdersByStatus(OrderStatus status);

    void changeOrderStatus(UUID orderId, OrderStatus status) throws AppException;

    List<OrderResponseDTO> getOrdersByStatusAndCustomerId(OrderStatus status, UUID customerId);

    boolean existById(UUID orderId);
}

package vn.edu.hcmuaf.fit.fahabook.service.impl;

import static vn.edu.hcmuaf.fit.fahabook.entity.enums.OrderStatus.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import vn.edu.hcmuaf.fit.fahabook.dto.request.OrderDetailRequestDTO;
import vn.edu.hcmuaf.fit.fahabook.dto.request.OrderEditRequestDTO;
import vn.edu.hcmuaf.fit.fahabook.dto.request.OrderRequestDTO;
import vn.edu.hcmuaf.fit.fahabook.dto.response.OrderDetailResponseDTO;
import vn.edu.hcmuaf.fit.fahabook.dto.response.OrderResponseDTO;
import vn.edu.hcmuaf.fit.fahabook.dto.response.RevenueResponse;
import vn.edu.hcmuaf.fit.fahabook.entity.Order;
import vn.edu.hcmuaf.fit.fahabook.entity.OrderDetail;
import vn.edu.hcmuaf.fit.fahabook.entity.enums.OrderMethod;
import vn.edu.hcmuaf.fit.fahabook.entity.enums.OrderStatus;
import vn.edu.hcmuaf.fit.fahabook.entity.enums.StockAction;
import vn.edu.hcmuaf.fit.fahabook.event.RemoveCartEvent;
import vn.edu.hcmuaf.fit.fahabook.event.StockUpdateEvent;
import vn.edu.hcmuaf.fit.fahabook.exception.AppException;
import vn.edu.hcmuaf.fit.fahabook.exception.ErrorCode;
import vn.edu.hcmuaf.fit.fahabook.mapper.OrderMapper;
import vn.edu.hcmuaf.fit.fahabook.mapper.ProductMapper;
import vn.edu.hcmuaf.fit.fahabook.repository.CustomerRepository;
import vn.edu.hcmuaf.fit.fahabook.repository.OrderDetailRepository;
import vn.edu.hcmuaf.fit.fahabook.repository.OrderRepository;
import vn.edu.hcmuaf.fit.fahabook.service.OrderService;

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
    ApplicationEventPublisher applicationEventPublisher;

    @Override
    @PostAuthorize("@customerServiceImpl.getCustomerIdByUsername(authentication.name) == #dto.customerId")
    @PreAuthorize("hasRole('USER')")
    @Transactional
    public UUID saveOrder(OrderRequestDTO dto) throws AppException {

        BigDecimal amount =
                calcTotalAmount(dto.getOrderDetails(), discountService.getDiscountPercent(dto.getDiscountCode()));

        Order order = Order.builder()
                .customer(customerRepository
                        .findById(dto.getCustomerId())
                        .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND)))
                .orderDate(LocalDateTime.now())
                .totalAmount(amount)
                .address(dto.getAddress())
                .numberPhone(dto.getNumberPhone())
                .receiver(dto.getReceiver())
                .paymentMethod(dto.getPaymentMethod())
                .status(determineDefaultStatus(dto.getPaymentMethod()))
                .build();
        // Luu đon hàng
        Order savedOrder = orderRepository.save(order);
        // Thêm detail khi đã có orderId
        List<OrderDetail> details = dto.getOrderDetails().stream()
                .map(req -> {
                    OrderDetail od = convertToOrderDetailEntity(req);
                    od.setOrder(savedOrder);
                    return od;
                })
                .toList();

        orderDetailRepository.saveAll(details);
        applicationEventPublisher.publishEvent(
                new RemoveCartEvent(this, order.getCustomer().getId()));
        return savedOrder.getId();
    }

    // Tính toán tổng tiền đơn hàng từ chi tiết đơn hàng và mã giảm giá
    @Override
    public BigDecimal calcTotalAmount(List<OrderDetailRequestDTO> orderDetails, Integer discountPercent) {
        // tính tiền tổng
        BigDecimal totalAmount = orderDetails.stream()
                .map(detail -> {
                    // Kiểm tra tồn kho
                    applicationEventPublisher.publishEvent(new StockUpdateEvent(
                            this, detail.getProductId(), detail.getQuantity(), StockAction.DECREASE));

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
    @Transactional
    public void deleteOrder(UUID orderId) {
        changeOrderStatus(orderId, CANCELLED);
        List<OrderDetail> details = orderRepository
                .findById(orderId)
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND))
                .getOrderDetails();
        if (details.isEmpty()) {
            throw new AppException(ErrorCode.ORDER_NOT_FOUND);
        }
        details.forEach(d -> applicationEventPublisher.publishEvent(
                new StockUpdateEvent(this, d.getProduct().getId(), d.getQuantity(), StockAction.INCREASE)));

        orderDetailRepository.deleteAll(details);
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
        log.warn("List of orders: {}", orders.size());
        if (orders.isEmpty()) {
            return Collections.emptyList();
        }

        return orders.stream().map(this::convertToOrderResponseDTO).collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public void editOrder(UUID id, OrderEditRequestDTO orderEditRequestDTO) throws AppException {
        Order order = orderRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));
        order.setAddress(orderEditRequestDTO.getAddress());
        order.setNumberPhone(orderEditRequestDTO.getNumberPhone());
        order.setStatus(orderEditRequestDTO.getStatus());
        order.setReceiver(orderEditRequestDTO.getReceiver());
        orderRepository.save(order);
    }

    @Override
    public List<RevenueResponse> getMonthlyRevenue() {
        List<RevenueResponse> monthlyRevenues = new ArrayList<>();
        for (int month = 1; month <= 12; month++) {

            monthlyRevenues.add(getRevenueByMonth(month, LocalDate.now().getYear()));
        }
        return monthlyRevenues;
    }

    @Override
    public RevenueResponse getRevenueByMonth(int month, int year) {
        if (month < 1 || month > 12) {
            throw new AppException(ErrorCode.INVALID_TIME);
        }
        if (year < 2000 || year > LocalDate.now().getYear()) {
            throw new AppException(ErrorCode.INVALID_TIME);
        }

        LocalDateTime startOfMonth = LocalDate.of(year, month, 1).atStartOfDay();
        LocalDateTime endOfMonth = startOfMonth
                .withDayOfMonth(startOfMonth.toLocalDate().lengthOfMonth())
                .withHour(23)
                .withMinute(59)
                .withSecond(59);
        List<Order> order = orderRepository.findAllByOrderDateBetween(startOfMonth, endOfMonth).stream()
                .filter(o -> o.getStatus() == DELIVERED)
                .toList();
        return RevenueResponse.builder()
                .month(month)
                .revenue(order.stream().map(Order::getTotalAmount).reduce(BigDecimal.ZERO, BigDecimal::add))
                .build();
    }

    @Override
    public RevenueResponse getRevenueByYear(int year) {
        if (year < 2000 || year > LocalDate.now().getYear()) {
            throw new AppException(ErrorCode.INVALID_TIME);
        }
        LocalDateTime startOfYear = LocalDate.of(year, 1, 1).atStartOfDay();
        LocalDateTime endOfYear = startOfYear.plusYears(1).minusNanos(1);

        BigDecimal revenue = orderRepository.findAllByOrderDateBetween(startOfYear, endOfYear).stream()
                .filter(order -> order.getStatus() == DELIVERED)
                .map(Order::getTotalAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return RevenueResponse.builder().year(year).revenue(revenue).build();
    }

    @Override
    public RevenueResponse getRevenueByDate(LocalDate date) {
        LocalDateTime startOfDay = date.atStartOfDay(); // 00:00:00
        LocalDateTime endOfDay = date.atTime(23, 59, 59); // 23:59:59

        List<Order> orders = orderRepository.findAllByOrderDateBetween(startOfDay, endOfDay);

        BigDecimal revenue = orders.stream()
                .filter(o -> o.getStatus() == DELIVERED)
                .map(Order::getTotalAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return RevenueResponse.builder()
                .day(date.getDayOfMonth())
                .month(date.getMonthValue())
                .year(date.getYear())
                .revenue(revenue)
                .build();
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
    public void changeOrderStatus(UUID orderId, OrderStatus newStatus) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));

        OrderStatus currentStatus = order.getStatus();

        if (currentStatus == newStatus) {
            log.info("Trạng thái đơn hàng {} đã là {}, không cần cập nhật.", orderId, newStatus);
            return;
        }

        if (!currentStatus.canChangeTo(newStatus)) {
            log.warn("Không thể chuyển trạng thái từ {} sang {} cho đơn hàng {}", currentStatus, newStatus, orderId);
            return;
        }

        order.setStatus(newStatus);
        orderRepository.save(order);
        log.info("Đã cập nhật trạng thái đơn hàng {} từ {} sang {}", orderId, currentStatus, newStatus);
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

        return orders.stream().map(this::convertToOrderResponseDTO).collect(Collectors.toCollection(ArrayList::new));
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

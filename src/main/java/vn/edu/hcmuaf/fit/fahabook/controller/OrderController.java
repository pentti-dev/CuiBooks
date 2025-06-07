package vn.edu.hcmuaf.fit.fahabook.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import vn.edu.hcmuaf.fit.fahabook.dto.request.OrderDetailRequestDTO;
import vn.edu.hcmuaf.fit.fahabook.dto.request.OrderRequestDTO;
import vn.edu.hcmuaf.fit.fahabook.dto.response.ApiResponse;
import vn.edu.hcmuaf.fit.fahabook.dto.response.OrderResponseDTO;
import vn.edu.hcmuaf.fit.fahabook.entity.enums.OrderMethod;
import vn.edu.hcmuaf.fit.fahabook.entity.enums.OrderStatus;
import vn.edu.hcmuaf.fit.fahabook.exception.AppException;
import vn.edu.hcmuaf.fit.fahabook.service.DiscountService;
import vn.edu.hcmuaf.fit.fahabook.service.OrderService;

@Slf4j
@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
@Tag(name = "Order", description = "Order API")
@FieldDefaults(makeFinal = true, level = lombok.AccessLevel.PRIVATE)
@PreAuthorize("hasRole('USER')")
public class OrderController {
    OrderService orderService;
    DiscountService discountService;

    @Operation(summary = "Lưu đơn hàng")
    @PostMapping
    public ApiResponse<UUID> createOrder(
            @RequestParam OrderMethod method, @Valid @RequestBody OrderRequestDTO orderRequestDTO) throws AppException {
        orderRequestDTO.setPaymentMethod(method);

        return ApiResponse.<UUID>builder()
                .code(HttpStatus.OK.value())
                .data(orderService.saveOrder(orderRequestDTO))
                .build();
    }

    @Operation(summary = "Kiểm tra mã giảm giá")
    @PostMapping("/check-discount")
    public ApiResponse<Void> checkDiscountCode(@RequestParam String discountCode) {
        discountService.checkValidDiscount(discountCode);
        return ApiResponse.success();
    }

    @PostMapping("/total-amount")
    @Operation(summary = "Lấy tổng số tiền của đơn hàng")
    public ApiResponse<BigDecimal> getTotalAmount(
            @RequestParam String discountCode, @RequestBody List<OrderDetailRequestDTO> orderDetails) {
        Integer discountPercent = discountService.getDiscountPercent(discountCode);
        return ApiResponse.<BigDecimal>builder()
                .data(orderService.calcTotalAmount(orderDetails, discountPercent))
                .build();
    }

    @Operation(summary = "Lấy danh sách đơn hàng theo ID khách hàng")
    @GetMapping("/customer/{customerId}")
    public ApiResponse<List<OrderResponseDTO>> getOrderByCustomerId(@PathVariable UUID customerId) {
        return ApiResponse.<List<OrderResponseDTO>>builder()
                .data(orderService.getOrderByCustomerId(customerId))
                .build();
    }

    @Operation(summary = "Lấy danh sách đơn hàng theo trạng thái và ID khách hàng")
    @GetMapping("/client/{status}&&{customerId}")
    public ApiResponse<List<OrderResponseDTO>> getOrderByStatusAndCustomerId(
            @PathVariable OrderStatus status, @PathVariable UUID customerId) {

        return ApiResponse.<List<OrderResponseDTO>>builder()
                .data(orderService.getOrdersByStatusAndCustomerId(status, customerId))
                .build();
    }

    @Operation(summary = "Hủy đơn hàng")
    @DeleteMapping("/{orderId}")
    public ApiResponse<Void> deleteOrder(@PathVariable UUID orderId) {
        orderService.deleteOrder(orderId);
        return ApiResponse.success();
    }
}

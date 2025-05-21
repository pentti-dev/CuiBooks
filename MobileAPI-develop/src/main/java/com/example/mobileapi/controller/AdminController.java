package com.example.mobileapi.controller;

import com.example.mobileapi.entity.enums.OrderStatus;
import com.example.mobileapi.dto.request.CustomerRequestDTO;
import com.example.mobileapi.dto.request.OrderEditRequestDTO;
import com.example.mobileapi.dto.response.ApiResponse;
import com.example.mobileapi.dto.response.CustomerResponseDTO;
import com.example.mobileapi.dto.response.MonthlyRevenueResponse;
import com.example.mobileapi.dto.response.OrderResponseDTO;
import com.example.mobileapi.exception.AppException;
import com.example.mobileapi.service.AdminService;
import com.example.mobileapi.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@Tag(name = "Admin", description = "Admin API")
@FieldDefaults(makeFinal = true, level = lombok.AccessLevel.PRIVATE)
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {
    AdminService adminService;

    @Operation(summary = "Lấy số lượng người dùng")
    @GetMapping("/customers")
    public ApiResponse<List<CustomerResponseDTO>> getCustomers() {

        return ApiResponse.<List<CustomerResponseDTO>>builder()
                .data(adminService.getAllCustomers())
                .build();
    }

    @Operation(summary = "Lấy thông tin người dùng theo ID")
    @GetMapping("/customer/{customerId}")
    public ApiResponse<CustomerResponseDTO> getCustomer(@PathVariable UUID customerId) {
        return ApiResponse.<CustomerResponseDTO>builder()
                .data(adminService.getCustomer(customerId))
                .build();
    }

    @Operation(summary = "Thêm người dùng")
    @PostMapping("/customer")
    public ApiResponse<CustomerResponseDTO> addCustomer(@RequestBody @Valid CustomerRequestDTO customer)
            throws AppException {

        adminService.saveCustomer(customer);
        return ApiResponse.<CustomerResponseDTO>builder()
                .data(adminService.saveCustomer(customer))
                .build();
    }

    @Operation(summary = "Cập nhật thông tin người dùng")
    @PutMapping("/customer/{customerId}")
    public ApiResponse<CustomerResponseDTO> updateCustomer(@PathVariable UUID customerId,
                                                           @RequestBody CustomerRequestDTO customer) throws AppException {
        return ApiResponse.<CustomerResponseDTO>builder()
                .data(adminService.updateCustomer(customerId, customer))
                .build();
    }

    @Operation(summary = "Xóa người dùng")
    @DeleteMapping("/customer/{customerId}")
    public ApiResponse<Void> deleteCustomer(@PathVariable UUID customerId) {
        adminService.deleteCustomer(customerId);
        return ApiResponse.success("Xóa người dùng thành công");
    }

    OrderService orderService;

    @GetMapping("/order/revenue")
    @Operation(summary = "Lấy doanh thu theo tháng")
    public ApiResponse<List<MonthlyRevenueResponse>> getOrderRevenue() {

        return ApiResponse.<List<MonthlyRevenueResponse>>builder()
                .data(orderService.getMonthlyRevenue())
                .build();
    }

    @DeleteMapping("/order/{orderId}")
    @Operation(summary = "Hủy đơn hàng")
    public ApiResponse<Void> deleteOrder(@PathVariable("orderId") UUID orderId) {
        orderService.deleteOrder(orderId);
        return ApiResponse.success("Hủy đơn hàng thành công");
    }

    @Operation(summary = "Lấy danh sách đơn hàng")
    @GetMapping("/order/list")
    public ApiResponse<List<OrderResponseDTO>> getAllOrders() {

        return ApiResponse.<List<OrderResponseDTO>>builder()
                .data(orderService.getAllOrders())
                .build();

    }

    @Operation(summary = "Lấy đơn hàng theo ID")
    @PutMapping("/order/{orderId}")
    public ApiResponse<Void> editOrder(@RequestBody OrderEditRequestDTO orderRequestDTO,
                                       @PathVariable("orderId") UUID orderId) throws AppException {
        orderService.editOrder(orderId, orderRequestDTO);
        return ApiResponse.success("Cập nhật đơn hàng thành công");
    }

    @Operation(summary = "Lấy danh sách đơn hàng theo trạng thái")
    @GetMapping("/{status}")
    public ApiResponse<List<OrderResponseDTO>> getOrderByStatus(@PathVariable OrderStatus status) {
        return ApiResponse.<List<OrderResponseDTO>>builder()
                .data(orderService.getOrdersByStatus(status))
                .build();

    }

    @Operation(summary = "Cập nhật trạng thái đơn hàng")
    @PutMapping("/status/{status}/{orderId}")
    public ApiResponse<Void> changeOrderStatus(@PathVariable("status") OrderStatus status,
                                               @PathVariable("orderId") UUID orderId) {
        try {
            orderService.changeOrderStatus(orderId, status);
            return ApiResponse.success("Cập nhật trạng thái đơn hàng thành công");
        } catch (Exception e) {
            return ApiResponse.<Void>builder()
                    .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .message("Cập nhật trạng thái đơn hàng thất bại")
                    .build();

        }
    }


}

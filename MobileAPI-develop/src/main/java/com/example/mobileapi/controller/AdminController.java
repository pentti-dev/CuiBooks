package com.example.mobileapi.controller;

import com.example.mobileapi.common.OrderStatus;
import com.example.mobileapi.dto.request.CartRequestDTO;
import com.example.mobileapi.dto.request.CustomerRequestDTO;
import com.example.mobileapi.dto.request.OrderEditRequestDTO;
import com.example.mobileapi.dto.response.ApiResponse;
import com.example.mobileapi.dto.response.CustomerResponseDTO;
import com.example.mobileapi.dto.response.MonthlyRevenueResponse;
import com.example.mobileapi.dto.response.OrderResponseDTO;
import com.example.mobileapi.exception.AppException;
import com.example.mobileapi.exception.ErrorCode;
import com.example.mobileapi.service.CartService;
import com.example.mobileapi.service.CustomerService;
import com.example.mobileapi.service.OrderService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@Tag(name = "Admin", description = "Admin API")
@FieldDefaults(makeFinal = true, level = lombok.AccessLevel.PRIVATE)
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {
    CustomerService customerService;
    CartService cartService;

    @GetMapping("/customers")
    public ApiResponse<List<CustomerResponseDTO>> getCustomers() {

        return ApiResponse.<List<CustomerResponseDTO>>builder()
                .data(customerService.getAllCustomers())
                .build();
    }

    @GetMapping("/customer/{customerId}")
    public ApiResponse<CustomerResponseDTO> getCustomer(@PathVariable int customerId) {
        return ApiResponse.<CustomerResponseDTO>builder()
                .data(customerService.getCustomer(customerId))
                .build();
    }

    @PostMapping("/customer")
    public ApiResponse<Void> addCustomer(@RequestBody @Valid CustomerRequestDTO customer) throws AppException {
        if (customerService.checkUsername(customer.getUsername())) {
            throw new AppException(ErrorCode.USERNAME_EXISTED);
        } else if (customerService.checkEmail(customer.getEmail())) {
            throw new AppException(ErrorCode.EMAIL_EXISTED);
        }
        int userId = customerService.saveCustomer(customer);
        CartRequestDTO cartRequestDTO = new CartRequestDTO();
        cartRequestDTO.setCustomerId(userId);
        cartService.saveCart(cartRequestDTO);
        return ApiResponse.success("Thêm người dùng thành công");
    }

    @PutMapping("/customer/{customerId}")
    public ApiResponse<CustomerResponseDTO> updateCustomer(@PathVariable int customerId, @RequestBody CustomerRequestDTO customer) {
        try {
            return ApiResponse.<CustomerResponseDTO>builder()
                    .data(customerService.updateCustomerById(customerId, customer))
                    .build();
        } catch (AppException e) {
            throw new RuntimeException(e);
        }
    }

    @DeleteMapping("/customer/{customerId}")
    public ApiResponse<Void> deleteCustomer(@PathVariable int customerId) {
        customerService.deleteCustomer(customerId);
        return ApiResponse.success("Xóa người dùng thành công");
    }

    OrderService orderService;

    @GetMapping("/order/revenue")
    public ApiResponse<List<MonthlyRevenueResponse>> getOrderRevenue() {

        return ApiResponse.<List<MonthlyRevenueResponse>>builder()
                .data(orderService.getMonthlyRevenue())
                .build();
    }

    @DeleteMapping("/order/{orderId}")
    public ApiResponse<Void> deleteOrder(@PathVariable("orderId") int orderId) {
        orderService.deleteOrder(orderId);
        return ApiResponse.success("Hủy đơn hàng thành công");
    }

    @GetMapping("/order/list")
    public ApiResponse<List<OrderResponseDTO>> getAllOrders() {

        return ApiResponse.<List<OrderResponseDTO>>builder()
                .data(orderService.getAllOrders())
                .build();

    }
    @PutMapping("/order/{orderId}")
    public ApiResponse<Void> editOrder(@RequestBody OrderEditRequestDTO orderRequestDTO, @PathVariable("orderId") int orderId) {
        orderService.editOrder(orderId, orderRequestDTO);
        return ApiResponse.success("Cập nhật đơn hàng thành công");
    }
    @GetMapping("/{status}")
    public ApiResponse<List<OrderResponseDTO>> getOrderByStatus(@PathVariable OrderStatus status) {
        return ApiResponse.<List<OrderResponseDTO>>builder()
                .data(orderService.getOrdersByStatus(status.getValue()))
                .build();

    }
    @PutMapping("/status/{status}&&{orderId}")
    public ApiResponse<Void> changeOrderStatus(@PathVariable("status") OrderStatus status,
                                               @PathVariable("orderId") int orderId) {
        try {
            orderService.changeOrderStatus(orderId, status.getValue());
            return ApiResponse.success("Cập nhật trạng thái đơn hàng thành công");
        } catch (Exception e) {
            return ApiResponse.<Void>builder()
                    .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .message("Cập nhật trạng thái đơn hàng thất bại")
                    .build();

        }
    }


}

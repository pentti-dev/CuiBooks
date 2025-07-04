package vn.edu.hcmuaf.fit.fahabook.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vn.edu.hcmuaf.fit.fahabook.annotation.GetToken;
import vn.edu.hcmuaf.fit.fahabook.dto.request.ChangePasswordDto;
import vn.edu.hcmuaf.fit.fahabook.dto.request.update.UpdateCustomerDTO;
import vn.edu.hcmuaf.fit.fahabook.dto.response.ApiResponse;
import vn.edu.hcmuaf.fit.fahabook.dto.response.CustomerResponseDTO;
import vn.edu.hcmuaf.fit.fahabook.exception.AppException;
import vn.edu.hcmuaf.fit.fahabook.exception.ErrorCode;
import vn.edu.hcmuaf.fit.fahabook.service.CustomerService;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/customer")
@RequiredArgsConstructor
@Tag(name = "Customer", description = "Customer API")
@FieldDefaults(makeFinal = true, level = lombok.AccessLevel.PRIVATE)
@PreAuthorize("hasRole('USER')")
public class CustomerController {
    CustomerService customerService;

    @Operation(summary = "Lấy so luong  dùng theo ID")
    @GetMapping("/quantity/{customerId}")
    public ApiResponse<Integer> getQuantity(@PathVariable("customerId") UUID customerId) {
        return ApiResponse.<Integer>builder()
                .data(customerService.getQuantityByCustomerId(customerId))
                .build();
    }

    @Operation(summary = "Cập nhật thông tin người dùng")
    @PatchMapping("/{customerId}")
    public ApiResponse<CustomerResponseDTO> updateCustomer(
            @PathVariable UUID customerId
            , @RequestBody
            @Valid
            UpdateCustomerDTO customer) throws AppException {
        return ApiResponse.success(customerService.updateCustomer(customerId, customer));
    }

    @Operation(summary = "Lấy thông tin người dùng bằng token")
    @GetMapping("/profile")
    @PreAuthorize("permitAll()")
    public ApiResponse<CustomerResponseDTO> getCustomerFromToken(@Parameter(hidden = true) @GetToken String token)
            throws AppException {

        return ApiResponse.<CustomerResponseDTO>builder()
                .data(customerService.getCustomerProfile(token))
                .build();
    }

    @PreAuthorize("permitAll()")
    @Operation(summary = "Thay đổi mật khẩu")
    @PostMapping("/resetPassword/{username}")
    public ApiResponse<Void> resetPassword(
            @PathVariable String username, @RequestParam String resetCode, @RequestParam String newPassword) {
        try {
            customerService.resetPassword(username, resetCode, newPassword);
        } catch (AppException e) {
            throw new AppException(ErrorCode.SERVICE_UNAVAILABLE);
        }

        return ApiResponse.success("Đặt lại mật khẩu thành công");
    }

    @PreAuthorize("permitAll()")
    @Operation(summary = "Gửi mã xác nhận đặt lại mật khẩu")
    @PostMapping("/initPasswordReset/{username}")
    public ApiResponse<Void> initPasswordReset(@PathVariable String username) {
        try {
            customerService.initPasswordReset(username);
        } catch (AppException e) {
            throw new AppException(ErrorCode.SERVICE_UNAVAILABLE);
        }

        return ApiResponse.success("Gửi mã xác nhận thành công");
    }

    @Operation(summary = "Thay đổi mật khẩu")
    @PatchMapping("/changePassword/{customerId}")
    public ApiResponse<Void> changePassword(@PathVariable UUID customerId, @RequestBody ChangePasswordDto dto)
            throws AppException {
        customerService.changePassword(customerId, dto.getOldPassword(), dto.getNewPassword());

        return ApiResponse.success("Đổi mật khẩu thành công");
    }
}

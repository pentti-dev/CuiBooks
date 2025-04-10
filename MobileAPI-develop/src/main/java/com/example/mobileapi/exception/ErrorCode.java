package com.example.mobileapi.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;

/**
 * Danh sách mã lỗi của hệ thống kèm theo HTTP Status và thông điệp lỗi.
 * Mỗi lỗi có:
 * - httpStatus: Mã HTTP tương ứng (vd: 400 BAD REQUEST, 404 NOT FOUND, ...)
 * - errorCode: Mã lỗi riêng của hệ thống để phân biệt các lỗi cùng HTTP Status.
 * - message: Mô tả lỗi cụ thể.
 */
@RequiredArgsConstructor
@Getter
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public enum ErrorCode {

    /**
     * ========== LỖI CHUNG ==========
     */
    BAD_REQUEST(HttpStatus.BAD_REQUEST, 4000, "Yêu cầu không hợp lệ."),
    MISSING_PARAMETERS(HttpStatus.BAD_REQUEST, 4001, "Thiếu thông tin bắt buộc."),

    /**
     * ========== LỖI ĐĂNG KÝ ==========
     */
    MISSING_USERNAME(HttpStatus.BAD_REQUEST, 4100, "Tên người dùng không được để trống."),
    MISSING_PASSWORD(HttpStatus.BAD_REQUEST, 4101, "Mật khẩu không được để trống."),
    MISSING_CONFIRM_PASSWORD(HttpStatus.BAD_REQUEST, 4102, "Xác nhận mật khẩu không được để trống."),
    MISSING_BIRTHDATE(HttpStatus.BAD_REQUEST, 4103, "Ngày sinh không được để trống."),
    PASSWORD_MISMATCH(HttpStatus.BAD_REQUEST, 4104, "Mật khẩu và xác nhận mật khẩu không khớp."),
    PASSWORD_TOO_WEAK(HttpStatus.BAD_REQUEST, 4105, "Mật khẩu quá yếu."),
    INVALID_EMAIL(HttpStatus.BAD_REQUEST, 4106, "Email không hợp lệ."),
    USERNAME_EXISTED(HttpStatus.BAD_REQUEST, 4107, "Tên người dùng đã tồn tại."),
    EMAIL_ALREADY_REGISTERED(HttpStatus.BAD_REQUEST, 4108, "Email đã được đăng ký."),
    UNDERAGE_USER(HttpStatus.BAD_REQUEST, 4109, "Người dùng chưa đủ tuổi đăng ký."),
    MISSING_FULLNAME(HttpStatus.BAD_REQUEST, 4110, "Họ tên không được để trống."),
    MISSING_PHONE(HttpStatus.BAD_REQUEST, 4111, "Số điện thoại không được để trống."),
    INVALID_PHONE(HttpStatus.BAD_REQUEST, 4112, "Số điện thoại không hợp lệ."),
    INVALID_BIRTHDATE(HttpStatus.BAD_REQUEST, 4113, "Ngày sinh không hợp lệ."),
    EMAIL_EXISTED(HttpStatus.BAD_REQUEST, 4114, "Email đã tồn tại."),
    INVALID_RESET_CODE(HttpStatus.BAD_REQUEST, 4115, "Mã xác nhận không hợp lệ hoặc đã hết hạn."),

    MISSING_NEW_PASSWORD(HttpStatus.BAD_REQUEST, 4116, "Mật khẩu mới không được để trống."),

    WRONG_OLD_PASSWORD(HttpStatus.BAD_REQUEST, 4117, "Mật khẩu cũ không chính xác."),
    WRONG_PASSWORD(HttpStatus.BAD_REQUEST, 4118, "Mật khẩu không chính xác."),

    /**
     * ========== LỖI XÁC THỰC ==========
     */
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, 4200, "Bạn chưa đăng nhập."),

    INVALID_CREDENTIALS(HttpStatus.UNAUTHORIZED, 4201, "Thông tin đăng nhập không hợp lệ."),

    TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, 4202, "Phiên đăng nhập đã hết hạn."),

    ACCOUNT_LOCKED(HttpStatus.UNAUTHORIZED, 4203, "Tài khoản của bạn đã bị khóa."),

    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, 4204, "Lỗi xác thực đăng nhập."),

    /**
     * ========== LỖI QUYỀN HẠN ==========
     */
    FORBIDDEN(HttpStatus.FORBIDDEN, 4300, "Bạn không có quyền truy cập."),

    INSUFFICIENT_PERMISSIONS(HttpStatus.FORBIDDEN, 4301, "Bạn không có quyền thực hiện thao tác này."),

    /**
     * ========== LỖI DỮ LIỆU ==========
     */
    RESOURCE_NOT_FOUND(HttpStatus.NOT_FOUND, 4400, "Không tìm thấy dữ liệu."),

    USER_NOT_FOUND(HttpStatus.NOT_FOUND, 4401, "Người dùng không tồn tại."),

    DUPLICATE_ENTRY(HttpStatus.CONFLICT, 4500, "Dữ liệu đã tồn tại."),
    INVALID_CART(HttpStatus.BAD_REQUEST, 4501, "Giỏ hàng không hợp lệ."),

    /**
     * ========== LỖI HỆ THỐNG ==========
     */
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, 5000, "Lỗi hệ thống."),

    DATABASE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, 5001, "Lỗi kết nối cơ sở dữ liệu."),

    BAD_GATEWAY(HttpStatus.BAD_GATEWAY, 5020, "Máy chủ phản hồi không hợp lệ."),

    SERVICE_UNAVAILABLE(HttpStatus.SERVICE_UNAVAILABLE, 5030, "Dịch vụ hiện không khả dụng."),

    TIMEOUT_ERROR(HttpStatus.GATEWAY_TIMEOUT, 5040, "Hệ thống phản hồi chậm.");

    HttpStatus httpStatus;

    int code;

    String message;
}

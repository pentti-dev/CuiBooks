package com.example.mobileapi.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RequiredArgsConstructor
@Getter
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public enum ErrorCode {
    // Lỗi chung
    BAD_REQUEST(400, 4000, "Yêu cầu không hợp lệ."),
    MISSING_PARAMETERS(400, 4001, "Thiếu thông tin bắt buộc."),

    // Lỗi liên quan đến đăng ký
    MISSING_USERNAME(400, 4100, "Tên người dùng không được để trống."),
    MISSING_PASSWORD(400, 4101, "Mật khẩu không được để trống."),
    MISSING_CONFIRM_PASSWORD(400, 4102, "Xác nhận mật khẩu không được để trống."),
    MISSING_BIRTHDATE(400, 4103, "Ngày sinh không được để trống."),
    PASSWORD_MISMATCH(400, 4104, "Mật khẩu và xác nhận mật khẩu không khớp."),
    PASSWORD_TOO_WEAK(400, 4105, "Mật khẩu quá yếu."),
    INVALID_EMAIL(400, 4106, "Email không hợp lệ."),
    USERNAME_TAKEN(400, 4107, "Tên người dùng đã tồn tại."),
    EMAIL_ALREADY_REGISTERED(400, 4108, "Email đã được đăng ký."),
    UNDERAGE_USER(400, 4109, "Người dùng chưa đủ tuổi đăng ký."),
    MISSING_FULLNAME(400, 4110, "Họ tên không được để trống."),
    MISSING_PHONE(400, 4111, "Số điện thoại không được để trống."),
    INVALID_PHONE(400, 4112, "Số điện thoại không hợp lệ."),
    INVALID_BIRTHDATE(400, 4113, "Ngày sinh không hợp lệ."),
    EMAIL_TAKEN(400, 4114, "Email đã tồn tại."),


    // Lỗi liên quan đến xác thực
    UNAUTHORIZED(401, 4200, "Bạn chưa đăng nhập."),
    INVALID_CREDENTIALS(401, 4201, "Thông tin đăng nhập không hợp lệ."),
    TOKEN_EXPIRED(401, 4202, "Phiên đăng nhập đã hết hạn."),
    ACCOUNT_LOCKED(401, 4203, "Tài khoản của bạn đã bị khóa."),

    // Lỗi quyền hạn
    FORBIDDEN(403, 4300, "Bạn không có quyền truy cập."),
    INSUFFICIENT_PERMISSIONS(403, 4301, "Bạn không có quyền thực hiện thao tác này."),

    // Lỗi dữ liệu
    RESOURCE_NOT_FOUND(404, 4400, "Không tìm thấy dữ liệu."),
    USER_NOT_FOUND(404, 4401, "Người dùng không tồn tại."),
    DUPLICATE_ENTRY(409, 4500, "Dữ liệu đã tồn tại."),

    // Lỗi hệ thống
    INTERNAL_SERVER_ERROR(500, 5000, "Lỗi hệ thống."),
    DATABASE_ERROR(500, 5001, "Lỗi kết nối cơ sở dữ liệu."),
    BAD_GATEWAY(502, 5020, "Máy chủ phản hồi không hợp lệ."),
    SERVICE_UNAVAILABLE(503, 5030, "Dịch vụ hiện không khả dụng."),
    TIMEOUT_ERROR(504, 5040, "Hệ thống phản hồi chậm.");

    int httpStatus;
    int errorCode;
    String message;
}

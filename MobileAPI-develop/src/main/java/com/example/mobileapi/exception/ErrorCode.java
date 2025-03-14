package com.example.mobileapi.exception;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RequiredArgsConstructor
@Getter
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public enum ErrorCode {
    BAD_REQUEST(400, 4000, "Yêu cầu không hợp lệ."),
    MISSING_PARAMETERS(400, 4001, "Thiếu thông tin bắt buộc."),
    INVALID_EMAIL(400, 4002, "Email không hợp lệ."),
    PASSWORD_TOO_WEAK(400, 4003, "Mật khẩu quá yếu."),
    UNAUTHORIZED(401, 4010, "Bạn chưa đăng nhập."),
    TOKEN_EXPIRED(401, 4011, "Phiên đăng nhập đã hết hạn."),
    FORBIDDEN(403, 4030, "Bạn không có quyền truy cập."),
    INSUFFICIENT_PERMISSIONS(403, 4031, "Bạn không có quyền thực hiện thao tác này."),
    RESOURCE_NOT_FOUND(404, 4040, "Không tìm thấy dữ liệu."),
    USER_NOT_FOUND(404, 4041, "Người dùng không tồn tại."),
    DUPLICATE_ENTRY(409, 4090, "Dữ liệu đã tồn tại."),

    INTERNAL_SERVER_ERROR(500, 5000, "Lỗi hệ thống."),
    DATABASE_ERROR(500, 5001, "Lỗi kết nối cơ sở dữ liệu."),
    BAD_GATEWAY(502, 5020, "Máy chủ phản hồi không hợp lệ."),
    SERVICE_UNAVAILABLE(503, 5030, "Dịch vụ hiện không khả dụng."),
    TIMEOUT_ERROR(504, 5040, "Hệ thống phản hồi chậm.");

    int httpStatus;
    int errorCode;
    String message;
}

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
    USERNAME_EXISTED(HttpStatus.BAD_REQUEST, 4107, "Tên đăng nhập đã tồn tại."),
    EMAIL_ALREADY_REGISTERED(HttpStatus.BAD_REQUEST, 4108, "Email đã được đăng ký."),
    UNDERAGE_USER(HttpStatus.BAD_REQUEST, 4109, "Người dùng chưa đủ tuổi đăng ký."),
    MISSING_FULLNAME(HttpStatus.BAD_REQUEST, 4110, "Họ tên không được để trống."),
    MISSING_PHONE(HttpStatus.BAD_REQUEST, 4111, "Số điện thoại không được để trống."),
    INVALID_PHONE(HttpStatus.BAD_REQUEST, 4112, "Số điện thoại không hợp lệ."),
    INVALID_BIRTHDATE(HttpStatus.BAD_REQUEST, 4113, "Ngày sinh không hợp lệ."),
    EMAIL_EXISTED(HttpStatus.BAD_REQUEST, 4114, "Email đã tồn tại."),
    INVALID_RESET_CODE(HttpStatus.BAD_REQUEST, 4115, "Mã xác nhận không hợp lệ hoặc đã hết hạn."),
    INVALD_DISCOUNT(HttpStatus.BAD_REQUEST, 4115, "Mã giảm giá không hợp lệ hoặc đã hết hạn."),
    INVALID_ORDER_AMOUNT(HttpStatus.BAD_REQUEST, 4115, "Số lượng đơn hàng không hợp lệ."),

    MISSING_NEW_PASSWORD(HttpStatus.BAD_REQUEST, 4116, "Mật khẩu mới không được để trống."),

    WRONG_OLD_PASSWORD(HttpStatus.BAD_REQUEST, 4117, "Mật khẩu cũ không chính xác."),
    WRONG_PASSWORD(HttpStatus.BAD_REQUEST, 4118, "Mật khẩu không chính xác."),
    INVALID_PASSWORD(HttpStatus.BAD_REQUEST, 4118, "Mật khẩu không hợp lệ. Phải có ít nhất 8 ký tự, bao gồm chữ hoa, chữ thường, số và ký tự đặc biệt."),
    MISSING_CATEGORY_NAME(HttpStatus.BAD_REQUEST, 4119, "Tên danh mục không được để trống."),
    MISSING_CATEGORY_CODE(HttpStatus.BAD_REQUEST, 4120, "Mã danh mục không được để trống."),
    MISSING_PRODUCT_NAME(HttpStatus.BAD_REQUEST, 4121, "Tên sản phẩm không được để trống."),
    MISSING_PRODUCT_PRICE(HttpStatus.BAD_REQUEST, 4122, "Giá sản phẩm không được để trống."),
    MISSING_PRODUCT_IMG(HttpStatus.BAD_REQUEST, 4123, "Ảnh sản phẩm không được để trống."),
    MISSING_PRODUCT_DETAIL(HttpStatus.BAD_REQUEST, 4124, "Chi tiết sản phẩm không được để trống."),
    MISSING_PRODUCT_AUTHOR(HttpStatus.BAD_REQUEST, 4125, "Tác giả sản phẩm không được để trống."),
    MISSING_PRODUCT_PUBLISH_YEAR(HttpStatus.BAD_REQUEST, 4126, "Năm xuất bản sản phẩm không được để trống."),
    MISSING_PRODUCT_PUBLISHER(HttpStatus.BAD_REQUEST, 4127, "Nhà xuất bản sản phẩm không được để trống."),
    MISSING_PRODUCT_LANGUAGE(HttpStatus.BAD_REQUEST, 4128, "Ngôn ngữ sản phẩm không được để trống."),
    MISSING_PRODUCT_WEIGHT(HttpStatus.BAD_REQUEST, 4129, "Trọng lượng sản phẩm không được để trống."),
    MISSING_PRODUCT_SIZE(HttpStatus.BAD_REQUEST, 4130, "Kích thước sản phẩm không được để trống."),
    MISSING_PRODUCT_PAGE_NUMBER(HttpStatus.BAD_REQUEST, 4131, "Số trang sản phẩm không được để trống."),
    MISSING_PRODUCT_FORM(HttpStatus.BAD_REQUEST, 4132, "Hình thức sản phẩm không được để trống."),
    MISSING_PRODUCT_SUPPLIER(HttpStatus.BAD_REQUEST, 4127, "Nhà cung cấp sản phẩm không được để trống."),
    MISSING_PRODUCT_DISCOUNT(HttpStatus.BAD_REQUEST, 4133, "Giảm giá sản phẩm không được để trống."),
    MISSING_PRODUCT_CATEGORY(HttpStatus.BAD_REQUEST, 4134, "Danh mục sản phẩm không được để trống."),
    MISSING_PRODUCT_ID(HttpStatus.BAD_REQUEST, 4135, "ID sản phẩm không được để trống."),
    ERROR_DURING_SEND_EMAIL(HttpStatus.INTERNAL_SERVER_ERROR, 4119, "Lỗi trong quá trình gửi email."),
    MISSING_EMAIL(HttpStatus.BAD_REQUEST, 4120, "Email không được để trống."),
    MISSING_CODE_DISCOUNT(HttpStatus.BAD_REQUEST, 4121, "Mã giảm giá không được để trống."),
    MISSING_PERCENT(HttpStatus.BAD_REQUEST, 4122, "Phần trăm giảm giá không được để trống."),
    INVALID_PERCENT(HttpStatus.BAD_REQUEST, 4123, "Phần trăm giảm giá không hợp lệ(1-100)."),
    INVALID_CODE_DISCOUNT(HttpStatus.BAD_REQUEST, 4124, "Mã giảm giá không hợp lệ. Tối đa 10 ký tự!"),
    INVALID_DATA(HttpStatus.BAD_REQUEST, 4125, "Dữ liệu không hợp lệ."),
    INVALID_PRODUCT_PUBLIC_YEAR(HttpStatus.BAD_REQUEST, 4126, "Năm xuất bản sản phẩm không hợp lệ. "),
    INVALID_PRODUCT_PRICE(HttpStatus.BAD_REQUEST, 4127, "Giá sản phẩm không hợp lệ. Phải lớn hơn 50000"),
    EXPIRED_DISCOUNT(HttpStatus.BAD_REQUEST, 4128, "Mã giảm giá đã hết hạn."),
    MISSING_START_DATE(HttpStatus.BAD_REQUEST, 4129, "Ngày bắt đầu không được để trống."),
    MISSING_END_DATE(HttpStatus.BAD_REQUEST, 4130, "Ngày kết thúc không được để trống."),
    INVALID_START_DATE(HttpStatus.BAD_REQUEST, 4131, "Ngày bắt đầu không hợp lệ. Phải là ngày hiện tại hoặc tương lai."),
    INVALID_END_DATE(HttpStatus.BAD_REQUEST, 4132, "Ngày kết thúc không hợp lệ. Phải là ngày sau ngày bắt đầu."),
    DISCOUNT_NOT_STARTED(HttpStatus.BAD_REQUEST, 4133, "Mã giảm giá chưa bắt đầu."),
    DISCOUNT_INACTIVE(HttpStatus.BAD_REQUEST, 4134, "Mã giảm giá không hoạt động."),
    INVALID_DATE_FORMAT(HttpStatus.BAD_REQUEST, 4135, "Định dạng ngày không hợp lệ."),
    MISSING_ADDRESS(HttpStatus.BAD_REQUEST, 4136, "Địa chỉ không được để trống."),
    MISSING_NUMBER_PHONE(HttpStatus.BAD_REQUEST, 4137, "Số điện thoại không được để trống."),
    MISSING_RECEIVER(HttpStatus.BAD_REQUEST, 4138, "Người nhận không được để trống."),
    MISSING_ORDER_DETAILS(HttpStatus.BAD_REQUEST, 4139, "Chi tiết đơn hàng không được để trống."),
    PHONE_EXISTED(HttpStatus.BAD_REQUEST, 4140, "Số điện thoại đã tồn tại."),
    QUANTITY_MUST_BE_POSITIVE(HttpStatus.BAD_REQUEST, 4141, "Số lượng sản phẩm phải là số dương."),
    MISSING_PRODUCT(HttpStatus.BAD_REQUEST, 4142, "Sản phẩm không được để trống."),
    MISSING_CUSTOMER(HttpStatus.BAD_REQUEST, 4143, "Khách hàng không được để trống."),
    MISSING_COMMENT(HttpStatus.BAD_REQUEST, 4144, "Bình luận không được để trống."),
    INVALID_COMMENT_LENGTH(HttpStatus.BAD_REQUEST, 4145, "Độ dài bình luận không hợp lệ. Phải từ 10 đến 500 ký tự."),
    INVALID_SCORE(HttpStatus.BAD_REQUEST, 4146, "Điểm đánh giá không hợp lệ. Phải từ 1 đến 5."),
    DISCOUNT_EXISTED(HttpStatus.BAD_REQUEST, 4147, "Mã giảm giá đã tồn tại."),
    INVALID_TIME(HttpStatus.BAD_REQUEST, 4148, "Thời gian không hợp lệ."),
    INVALID_PRODUCT_WEIGHT(HttpStatus.BAD_REQUEST, 4149, "Trọng lượng sản phẩm không hợp lệ. Phải là số dương."),
    INVALID_PRODUCT_SIZE(HttpStatus.BAD_REQUEST, 4150, "Kích thước sản phẩm không hợp lệ."),
    INVALID_PRODUCT_PAGE_NUMBER(HttpStatus.BAD_REQUEST, 4151, "Số trang sản phẩm không hợp lệ. Phải là số dương."),
    INVALID_PRODUCT_FORM(HttpStatus.BAD_REQUEST, 4152, "Hình thức sản phẩm không hợp lệ."),
    OUT_OF_STOCK(HttpStatus.BAD_REQUEST, 4153, "Sản phẩm đã hết hàng."),
    INSUFFICIENT_STOCK(HttpStatus.BAD_REQUEST, 4154, "Số lượng sản phẩm trong kho không đủ."),
    ORDER_CANNOT_CANCELLED(HttpStatus.BAD_REQUEST, 4155, "Đơn hàng không thể hủy. Đơn hàng đã được xử lý hoặc giao hàng."),
    INVALID_ORDER_STATUS(HttpStatus.BAD_REQUEST, 4156, "Trạng thái đơn hàng không hợp lệ."),
    INVALID_ORDER_METHOD(HttpStatus.BAD_REQUEST, 4157, "Phương thức đặt hàng không hợp lệ."),
    INVALID_CHANGE_ORDER_STATUS(HttpStatus.BAD_REQUEST, 4158, "Không thể thay đổi trạng thái đơn hàng từ trạng thái hiện tại sang trạng thái mới."),
    STOCK_UNVAILABLE(HttpStatus.BAD_REQUEST, 4159, "Số lượng sản phẩm không đủ trong kho để thực hiện thao tác."),
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
    INVALID_ORDER(HttpStatus.BAD_REQUEST, 4502, "Đơn hàng không hợp lệ."),
    ORDER_NOT_FOUND(HttpStatus.NOT_FOUND, 4503, "Đơn hàng không tồn tại."),
    ADDRESS_NOT_FOUND(HttpStatus.NOT_FOUND, 4504, "Địa chỉ không tồn tại."),
    CATEGORY_NOT_FOUND(HttpStatus.NOT_FOUND, 4505, "Danh mục không tồn tại."),
    PRODUCT_NOT_FOUND(HttpStatus.NOT_FOUND, 4506, "Sản phẩm không tồn tại."),
    ORDER_DETAIL_NOT_FOUND(HttpStatus.NOT_FOUND, 4507, "Chi tiết đơn hàng không tồn tại."),
    CATEGORY_NOT_FOUND_BY_ID(HttpStatus.NOT_FOUND, 4508, "Không tìm thấy danh mục với ID đã cho."),
    DISCOUNT_NOT_FOUND(HttpStatus.NOT_FOUND, 4509, "Không tìm thấy mã giảm giá."),
    CATEGORY_EXISTED(HttpStatus.BAD_REQUEST, 4510, "Danh mục đã tồn tại."),
    /**
     * ========== LỖI HỆ THỐNG ==========
     */
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, 500, "Lỗi hệ thống."),

    DATABASE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, 5001, "Lỗi kết nối cơ sở dữ liệu."),

    BAD_GATEWAY(HttpStatus.BAD_GATEWAY, 5020, "Máy chủ phản hồi không hợp lệ."),

    SERVICE_UNAVAILABLE(HttpStatus.SERVICE_UNAVAILABLE, 5030, "Dịch vụ hiện không khả dụng."),

    TIMEOUT_ERROR(HttpStatus.GATEWAY_TIMEOUT, 5040, "Hệ thống phản hồi chậm."),
    ;

    HttpStatus httpStatus;

    int code;

    String message;
}

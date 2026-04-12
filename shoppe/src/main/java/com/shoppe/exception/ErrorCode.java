package com.shoppe.exception;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum ErrorCode {

    // Auth
    USER_NOT_FOUND(404, "User không tồn tại", HttpStatus.NOT_FOUND),
    EMAIL_ALREADY_EXISTS(1002, "Email đã tồn tại", HttpStatus.CONFLICT),
    WRONG_PASSWORD(1003, "Mật khẩu không đúng", HttpStatus.UNAUTHORIZED),
    UNAUTHENTICATED(1004, "Chưa đăng nhập", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(1005, "Bạn không có quyền thực hiện thao tác này", HttpStatus.FORBIDDEN),

    // Validation
    INVALID_EMAIL(2001, "Email không hợp lệ", HttpStatus.BAD_REQUEST),
    INVALID_PASSWORD(2002, "Mật khẩu phải ít nhất 6 ký tự", HttpStatus.BAD_REQUEST),

    // Shop & Product
    SHOP_NOT_FOUND(3001, "Shop không tồn tại", HttpStatus.NOT_FOUND),
    PRODUCT_NOT_FOUND(3002, "Sản phẩm không tồn tại", HttpStatus.NOT_FOUND),
    PRODUCT_NOT_IN_SHOP(3003, "Sản phẩm không thuộc shop này", HttpStatus.BAD_REQUEST),
    PRODUCT_NAME_DUPLICATE(3004, "Tên sản phẩm đã tồn tại trong shop", HttpStatus.CONFLICT),
    PRODUCT_INACTIVE(3005, "Sản phẩm đã ngừng bán", HttpStatus.BAD_REQUEST),
    OUT_OF_STOCK(3006, "Sản phẩm không đủ tồn kho", HttpStatus.BAD_REQUEST),

    // Order
    ORDER_NOT_FOUND(4001, "Đơn hàng không tồn tại", HttpStatus.NOT_FOUND),
    ORDER_CANNOT_CANCEL(4002, "Đơn hàng không thể huỷ ở trạng thái hiện tại", HttpStatus.BAD_REQUEST),
    ORDER_STATUS_INVALID(4003, "Chuyển trạng thái không hợp lệ", HttpStatus.BAD_REQUEST),

    // Server
    INTERNAL_ERROR(9999, "Lỗi hệ thống", HttpStatus.INTERNAL_SERVER_ERROR);

    int code;
    String message;
    HttpStatus httpStatus;
}
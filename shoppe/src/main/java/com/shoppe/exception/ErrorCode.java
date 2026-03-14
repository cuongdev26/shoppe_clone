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

    // Auth errors
    USER_NOT_FOUND(404, "UserNotFound", HttpStatus.NOT_FOUND),
    EMAIL_ALREADY_EXISTS(1002, "Email đã tồn tại", HttpStatus.CONFLICT),
    WRONG_PASSWORD(1003, "Mật khẩu không đúng", HttpStatus.UNAUTHORIZED),
    UNAUTHENTICATED(1004, "Chưa đăng nhập", HttpStatus.UNAUTHORIZED),

    // Validation errors
    INVALID_EMAIL(2001, "Email không hợp lệ", HttpStatus.BAD_REQUEST),
    INVALID_PASSWORD(2002, "Mật khẩu phải ít nhất 6 ký tự", HttpStatus.BAD_REQUEST),

    // Server errors
    INTERNAL_ERROR(9999, "Lỗi hệ thống", HttpStatus.INTERNAL_SERVER_ERROR);



    int code;
    String message;
    HttpStatus httpStatus;
}
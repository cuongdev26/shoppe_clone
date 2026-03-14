package com.shoppe.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AppException.class)
    public ResponseEntity<Map<String, Object>> handleAppException(AppException ex) {
        ErrorCode error = ex.getErrorCode();
        return ResponseEntity
                .status(error.getHttpStatus())
                .body(Map.of(
                        "code", error.getCode(),
                        "message", error.getMessage()
                ));
    }
}
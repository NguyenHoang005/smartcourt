package com.smartcourt.exception;

import org.springframework.http.HttpStatus;

/** Dùng cho dữ liệu đầu vào hoặc quy tắc nghiệp vụ không hợp lệ (HTTP 400). */
public class BadRequestException extends AppException {

    public BadRequestException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }
}

package com.smartcourt.exception;

import org.springframework.http.HttpStatus;

/** Dùng khi xảy ra xung đột dữ liệu: trùng email, trùng lịch đặt sân (HTTP 409). */
public class ConflictException extends AppException {

    public ConflictException(String message) {
        super(HttpStatus.CONFLICT, message);
    }
}

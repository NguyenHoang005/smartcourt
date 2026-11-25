package com.smartcourt.exception;

import org.springframework.http.HttpStatus;

/** Dùng khi không tìm thấy bản ghi trong database (HTTP 404). */
public class ResourceNotFoundException extends AppException {

    public ResourceNotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }
}

package com.smartcourt.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * Exception nền tảng cho các lỗi nghiệp vụ có chủ đích.
 * Gắn kèm HttpStatus để GlobalExceptionHandler chuyển thẳng thành mã HTTP tương ứng.
 */
@Getter
public class AppException extends RuntimeException {

    private final HttpStatus status;

    public AppException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }
}

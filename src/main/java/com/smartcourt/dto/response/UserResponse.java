package com.smartcourt.dto.response;

import com.smartcourt.entity.User;
import com.smartcourt.enums.UserRole;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * Thông tin người dùng trả về cho Client (tuyệt đối không bao gồm mật khẩu).
 */
@Getter
@Builder
public class UserResponse {

    private Long id;
    private String fullName;
    private String email;
    private String phone;
    private UserRole role;
    private Boolean isActive;
    private LocalDateTime createdAt;

    public static UserResponse from(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .role(user.getRole())
                .isActive(user.getIsActive())
                .createdAt(user.getCreatedAt())
                .build();
    }
}

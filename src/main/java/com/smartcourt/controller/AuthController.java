package com.smartcourt.controller;

import com.smartcourt.dto.request.LoginRequest;
import com.smartcourt.dto.request.RegisterRequest;
import com.smartcourt.dto.response.ApiResponse;
import com.smartcourt.dto.response.AuthResponse;
import com.smartcourt.dto.response.UserResponse;
import com.smartcourt.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<AuthResponse>> register(@Valid @RequestBody RegisterRequest request) {
        AuthResponse response = authService.register(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success("Đăng ký tài khoản thành công", response));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@Valid @RequestBody LoginRequest request) {
        AuthResponse response = authService.login(request);
        return ResponseEntity.ok(ApiResponse.success("Đăng nhập thành công", response));
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<Void>> logout() {
        // Ở giai đoạn MVP (JWT Stateless), Client chỉ cần xóa token ở trình duyệt.
        // Giai đoạn mở rộng sẽ bổ sung lưu jti vào Redis Blacklist tại đây.
        return ResponseEntity.ok(ApiResponse.success("Đăng xuất thành công"));
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserResponse>> getCurrentUser(Authentication authentication) {
        UserResponse response = authService.getCurrentUser(authentication.getName());
        return ResponseEntity.ok(ApiResponse.success(response));
    }
}

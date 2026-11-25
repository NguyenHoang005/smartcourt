package com.smartcourt.service;

import com.smartcourt.dto.request.LoginRequest;
import com.smartcourt.dto.request.RegisterRequest;
import com.smartcourt.dto.response.AuthResponse;
import com.smartcourt.dto.response.UserResponse;
import com.smartcourt.entity.User;
import com.smartcourt.enums.UserRole;
import com.smartcourt.exception.ConflictException;
import com.smartcourt.exception.ResourceNotFoundException;
import com.smartcourt.repository.UserRepository;
import com.smartcourt.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Transactional
    public AuthResponse register(RegisterRequest request) {
        String normalizedEmail = request.getEmail().trim().toLowerCase();

        if (userRepository.existsByEmail(normalizedEmail)) {
            throw new ConflictException("Email đã được sử dụng");
        }

        User user = User.builder()
                .fullName(request.getFullName().trim())
                .email(normalizedEmail)
                .password(passwordEncoder.encode(request.getPassword()))
                .phone(request.getPhone().trim())
                .role(UserRole.USER)
                .isActive(true)
                .build();

        User savedUser = userRepository.save(user);
        return buildAuthResponse(savedUser);
    }

    public AuthResponse login(LoginRequest request) {
        String normalizedEmail = request.getEmail().trim().toLowerCase();

        // Spring Security tự kiểm tra mật khẩu (BCrypt) và trạng thái isActive;
        // Nếu sai sẽ tự ném AuthenticationException -> GlobalExceptionHandler xử lý trả 401.
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(normalizedEmail, request.getPassword())
        );

        User user = findUserByEmail(normalizedEmail);
        return buildAuthResponse(user);
    }

    public UserResponse getCurrentUser(String email) {
        return UserResponse.from(findUserByEmail(email));
    }

    private User findUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy người dùng"));
    }

    private AuthResponse buildAuthResponse(User user) {
        String token = jwtService.generateToken(user);
        return AuthResponse.builder()
                .accessToken(token)
                .user(UserResponse.from(user))
                .build();
    }
}

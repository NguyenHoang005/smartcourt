package com.smartcourt.entity;

import com.smartcourt.enums.BookingStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "bookings", indexes = {
        @Index(name = "idx_bookings_code", columnList = "booking_code", unique = true),
        @Index(name = "idx_bookings_court_status_time", columnList = "court_id, status, start_time, end_time"),
        @Index(name = "idx_bookings_status_expired", columnList = "status, expired_at"),
        @Index(name = "idx_bookings_user_created", columnList = "user_id, created_at")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Booking extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "booking_code", nullable = false, unique = true, length = 30)
    private String bookingCode;

    /**
     * FetchType.LAZY: Không load User khi query Booking trừ khi truy cập trực tiếp.
     * Tránh N+1 query khi lấy danh sách booking mà không cần thông tin user.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "court_id", nullable = false)
    private Court court;

    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalDateTime endTime;

    /** Snapshot giá tại thời điểm đặt. Admin đổi giá sau không ảnh hưởng booking cũ. */
    @Column(name = "total_price", nullable = false, precision = 12, scale = 2)
    private BigDecimal totalPrice;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 25)
    private BookingStatus status;

    /** Thời điểm hết hạn giữ chỗ. Chỉ có giá trị khi status = PENDING_PAYMENT. */
    @Column(name = "expired_at")
    private LocalDateTime expiredAt;

    /** Cờ chống gửi trùng email nhắc lịch. */
    @Column(name = "reminder_sent", nullable = false)
    @Builder.Default
    private Boolean reminderSent = false;

    @Column(name = "cancel_reason", length = 255)
    private String cancelReason;
}

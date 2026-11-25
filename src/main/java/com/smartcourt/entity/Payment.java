package com.smartcourt.entity;

import com.smartcourt.enums.PaymentMethod;
import com.smartcourt.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Mỗi lần User bấm thanh toán → tạo 1 Payment record.
 * Quan hệ: Booking (1) → (N) Payment
 *
 * Ví dụ: Lần 1 FAILED → tạo Payment #1 (FAILED)
 *         Lần 2 SUCCESS → tạo Payment #2 (SUCCESS) → Booking chuyển CONFIRMED
 */
@Entity
@Table(name = "payments", indexes = {
        @Index(name = "idx_payments_transaction_id", columnList = "transaction_id", unique = true),
        @Index(name = "idx_payments_booking_id", columnList = "booking_id")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booking_id", nullable = false)
    private Booking booking;

    /** Mã giao dịch duy nhất — Idempotency key chống xử lý trùng webhook. */
    @Column(name = "transaction_id", nullable = false, unique = true, length = 100)
    private String transactionId;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method", nullable = false, length = 30)
    private PaymentMethod paymentMethod;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 25)
    private PaymentStatus status;

    /** Thời điểm thanh toán thành công. null nếu chưa thanh toán hoặc thất bại. */
    @Column(name = "paid_at")
    private LocalDateTime paidAt;

    /** Lưu nguyên JSON callback từ cổng thanh toán để debug/audit. */
    @Column(name = "raw_callback_data", columnDefinition = "TEXT")
    private String rawCallbackData;
}

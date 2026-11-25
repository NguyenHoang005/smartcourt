package com.smartcourt.repository;

import com.smartcourt.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    /** Tìm payment theo mã giao dịch — dùng để chống xử lý trùng webhook. */
    Optional<Payment> findByTransactionId(String transactionId);

    List<Payment> findByBookingId(Long bookingId);
}

package com.smartcourt.repository;

import com.smartcourt.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    // Query kiểm tra trùng lịch và các query phức tạp sẽ được thêm ở Phase 4
}

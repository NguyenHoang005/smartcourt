package com.smartcourt.repository;

import com.smartcourt.entity.OperatingHours;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.DayOfWeek;
import java.util.Optional;

public interface OperatingHoursRepository extends JpaRepository<OperatingHours, Long> {

    Optional<OperatingHours> findByDayOfWeek(DayOfWeek dayOfWeek);
}

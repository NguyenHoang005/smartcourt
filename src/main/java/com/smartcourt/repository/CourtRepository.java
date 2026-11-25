package com.smartcourt.repository;

import com.smartcourt.entity.Court;
import com.smartcourt.enums.CourtStatus;
import com.smartcourt.enums.CourtType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourtRepository extends JpaRepository<Court, Long> {

    List<Court> findByStatus(CourtStatus status);

    List<Court> findByCourtTypeAndStatus(CourtType courtType, CourtStatus status);
}

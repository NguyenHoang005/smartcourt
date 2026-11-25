package com.smartcourt.entity;

import com.smartcourt.enums.CourtStatus;
import com.smartcourt.enums.CourtType;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "courts", indexes = {
        @Index(name = "idx_courts_status", columnList = "status"),
        @Index(name = "idx_courts_type_status", columnList = "court_type, status")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Court extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "court_type", nullable = false, length = 30)
    private CourtType courtType;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "price_per_hour", nullable = false, precision = 12, scale = 2)
    private BigDecimal pricePerHour;

    @Column(name = "min_duration_minutes", nullable = false)
    @Builder.Default
    private Integer minDurationMinutes = 60;

    @Column(name = "max_duration_minutes", nullable = false)
    @Builder.Default
    private Integer maxDurationMinutes = 180;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    @Builder.Default
    private CourtStatus status = CourtStatus.ACTIVE;
}

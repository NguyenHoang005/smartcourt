package com.smartcourt.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.DayOfWeek;
import java.time.LocalTime;

/**
 * Giờ hoạt động chung cho toàn bộ cụm sân.
 * Luôn có đúng 7 bản ghi (MONDAY → SUNDAY).
 *
 * Ví dụ:
 *   MONDAY    | 05:00 | 23:00 | false  → Thứ 2 mở cửa 05:00 - 23:00
 *   SUNDAY    | 06:00 | 22:00 | false  → Chủ nhật mở 06:00 - 22:00
 *   WEDNESDAY | null  | null  | true   → Thứ 4 đóng cửa
 */
@Entity
@Table(name = "operating_hours")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OperatingHours {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "day_of_week", nullable = false, unique = true, length = 15)
    private DayOfWeek dayOfWeek;

    @Column(name = "open_time", nullable = false)
    private LocalTime openTime;

    @Column(name = "close_time", nullable = false)
    private LocalTime closeTime;

    @Column(name = "is_closed", nullable = false)
    @Builder.Default
    private Boolean isClosed = false;
}

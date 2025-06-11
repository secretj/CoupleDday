package com.coupledday.domain.dday;

import com.coupledday.domain.couple.Couple;
import com.coupledday.domain.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "dday_events")
@Getter @Setter @NoArgsConstructor
public class DdayEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "couple_id")
    private Couple couple;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private String title;

    private String description;

    @Column(nullable = false)
    private LocalDate targetDate;

    @Enumerated(EnumType.STRING)
    private DdayType type; // ANNIVERSARY_100, ANNIVERSARY_365, CUSTOM, etc.

    private Integer customInterval; // 사용자 지정 간격 (일 단위)

    @Column(nullable = false)
    private Boolean isRecurring = false; // 반복 여부

    private String color; // 이벤트 색상

    private String googleCalendarEventId; // 구글 캘린더 연동용

    @Enumerated(EnumType.STRING)
    private EventStatus status = EventStatus.ACTIVE;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
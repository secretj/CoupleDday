package com.coupledday.domain.notification;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalTime;

@Entity
@Table(name = "notification_settings")
@Getter @Setter @NoArgsConstructor
public class NotificationSetting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dday_event_id")
    private DdayEvent ddayEvent;

    @Column(nullable = false)
    private Boolean isEnabled = true;

    private Integer daysBefore; // D-day 며칠 전에 알림

    private LocalTime notificationTime; // 알림 시간

    @Enumerated(EnumType.STRING)
    private NotificationType type; // PUSH, EMAIL, SMS

    private String message; // 커스텀 알림 메시지
}
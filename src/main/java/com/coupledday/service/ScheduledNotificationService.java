package com.coupledday.service;

import com.coupledday.domain.dday.DdayEvent;
import com.coupledday.repository.DdayEventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduledNotificationService {

    private final DdayEventRepository ddayEventRepository;
    private final NotificationService notificationService;

    // 매일 오전 9시에 D-day 알림 체크
    @Scheduled(cron = "0 0 9 * * *")
    public void checkAndSendDdayNotifications() {
        LocalDate today = LocalDate.now();

        // 향후 30일 내의 모든 이벤트 조회
        LocalDate endDate = today.plusDays(30);
        List<DdayEvent> upcomingEvents = ddayEventRepository
                .findUpcomingEvents(null, today, endDate); // 모든 커플의 이벤트

        for (DdayEvent event : upcomingEvents) {
            long daysRemaining = ChronoUnit.DAYS.between(today, event.getTargetDate());
            notificationService.sendDdayNotification(event, daysRemaining);
        }
    }

    // 매주 일요일 오후 8시에 주간 요약 발송
    @Scheduled(cron = "0 0 20 * * SUN")
    public void sendWeeklySummary() {
        // 주간 요약 로직 구현
    }
}
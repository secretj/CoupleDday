package com.coupledday.service;

import com.coupledday.domain.dday.DdayEvent;
import com.coupledday.domain.notification.NotificationSetting;
import com.coupledday.domain.notification.NotificationType;
import com.coupledday.repository.NotificationSettingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final JavaMailSender mailSender;
    private final NotificationSettingRepository notificationRepository;
    private final FCMService fcmService; // Firebase Cloud Messaging

    @Async
    public void sendDdayNotification(DdayEvent event, Long daysRemaining) {
        List<NotificationSetting> settings = notificationRepository
                .findByDdayEventIdAndIsEnabledTrue(event.getId());

        for (NotificationSetting setting : settings) {
            if (shouldSendNotification(setting, daysRemaining)) {
                switch (setting.getType()) {
                    case EMAIL -> sendEmailNotification(event, setting, daysRemaining);
                    case PUSH -> sendPushNotification(event, setting, daysRemaining);
                    case SMS -> sendSMSNotification(event, setting, daysRemaining);
                }
            }
        }
    }

    private boolean shouldSendNotification(NotificationSetting setting, Long daysRemaining) {
        return setting.getDaysBefore() == null ||
                daysRemaining.equals(setting.getDaysBefore().longValue());
    }

    private void sendEmailNotification(DdayEvent event, NotificationSetting setting, Long daysRemaining) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(setting.getUser().getEmail());
            message.setSubject("💕 D-day 알림: " + event.getTitle());

            String body = createNotificationMessage(event, daysRemaining, setting.getMessage());
            message.setText(body);

            mailSender.send(message);
        } catch (Exception e) {
            // 로그 처리
        }
    }

    private void sendPushNotification(DdayEvent event, NotificationSetting setting, Long daysRemaining) {
        String title = "💕 " + event.getTitle();
        String body = createNotificationMessage(event, daysRemaining, setting.getMessage());

        fcmService.sendNotification(setting.getUser().getId(), title, body);
    }

    private void sendSMSNotification(DdayEvent event, NotificationSetting setting, Long daysRemaining) {
        // SMS 발송 로직 구현 (외부 SMS 서비스 연동)
    }

    private String createNotificationMessage(DdayEvent event, Long daysRemaining, String customMessage) {
        if (customMessage != null && !customMessage.isEmpty()) {
            return customMessage;
        }

        if (daysRemaining == 0) {
            return "오늘은 " + event.getTitle() + " 입니다! 💕";
        } else if (daysRemaining > 0) {
            return event.getTitle() + "까지 " + daysRemaining + "일 남았습니다! 💕";
        } else {
            return event.getTitle() + "이 지난지 " + Math.abs(daysRemaining) + "일이 되었습니다! 💕";
        }
    }
}

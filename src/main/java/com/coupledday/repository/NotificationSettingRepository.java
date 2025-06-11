package com.coupledday.repository;

import com.coupledday.domain.notification.NotificationSetting;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface NotificationSettingRepository extends JpaRepository<NotificationSetting, Long> {
    List<NotificationSetting> findByDdayEventIdAndIsEnabledTrue(Long ddayEventId);
    List<NotificationSetting> findByUserIdAndIsEnabledTrue(Long userId);
}
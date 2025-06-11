package com.coupledday.service;

import com.coupledday.domain.dday.DdayEvent;
import com.coupledday.domain.dday.DdayType;
import com.coupledday.repository.DdayEventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Transactional
public class DdayEventService {

    private final DdayEventRepository ddayEventRepository;
    private final DdayCalculationService calculationService;
    private final GoogleCalendarService googleCalendarService;
    private final RedisTemplate<String, Object> redisTemplate;

    public DdayEvent createDdayEvent(Long coupleId, Long userId, String title,
                                     LocalDate targetDate, DdayType type, Integer customInterval) {

        DdayEvent event = new DdayEvent();
        // 엔티티 설정...

        DdayEvent savedEvent = ddayEventRepository.save(event);

        // Redis 캐시에 저장 (자주 조회되는 D-day 정보)
        String cacheKey = "dday:couple:" + coupleId;
        redisTemplate.opsForValue().set(cacheKey, savedEvent, 1, TimeUnit.HOURS);

        return savedEvent;
    }

    public List<DdayEvent> getUpcomingEvents(Long coupleId, int days) {
        String cacheKey = "upcoming:couple:" + coupleId + ":days:" + days;

        @SuppressWarnings("unchecked")
        List<DdayEvent> cachedEvents = (List<DdayEvent>) redisTemplate.opsForValue().get(cacheKey);

        if (cachedEvents != null) {
            return cachedEvents;
        }

        LocalDate endDate = LocalDate.now().plusDays(days);
        List<DdayEvent> events = ddayEventRepository.findUpcomingEvents(coupleId, LocalDate.now(), endDate);

        // 30분간 캐시
        redisTemplate.opsForValue().set(cacheKey, events, 30, TimeUnit.MINUTES);

        return events;
    }

    public void syncWithGoogleCalendar(Long eventId, String userAccessToken) {
        DdayEvent event = ddayEventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("이벤트를 찾을 수 없습니다"));

        if (event.getGoogleCalendarEventId() == null) {
            String googleEventId = googleCalendarService.createCalendarEvent(
                    userAccessToken,
                    event.getTitle(),
                    event.getDescription(),
                    event.getTargetDate().atStartOfDay(),
                    event.getTargetDate().atTime(23, 59)
            );

            event.setGoogleCalendarEventId(googleEventId);
            ddayEventRepository.save(event);
        }
    }
}
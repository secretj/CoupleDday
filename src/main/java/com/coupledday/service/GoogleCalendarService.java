package com.coupledday.service;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Service
public class GoogleCalendarService {

    public String createCalendarEvent(String accessToken, String title, String description,
                                      LocalDateTime startDateTime, LocalDateTime endDateTime) {
        try {
            Calendar service = getCalendarService(accessToken);

            Event event = new Event()
                    .setSummary(title)
                    .setDescription(description);

            EventDateTime start = new EventDateTime()
                    .setDateTime(new com.google.api.client.util.DateTime(
                            Date.from(startDateTime.atZone(ZoneId.systemDefault()).toInstant())))
                    .setTimeZone("Asia/Seoul");
            event.setStart(start);

            EventDateTime end = new EventDateTime()
                    .setDateTime(new com.google.api.client.util.DateTime(
                            Date.from(endDateTime.atZone(ZoneId.systemDefault()).toInstant())))
                    .setTimeZone("Asia/Seoul");
            event.setEnd(end);

            event = service.events().insert("primary", event).execute();
            return event.getId();

        } catch (Exception e) {
            throw new RuntimeException("구글 캘린더 이벤트 생성 실패", e);
        }
    }
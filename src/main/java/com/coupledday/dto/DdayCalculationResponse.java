package com.coupledday.dto;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import lombok.Builder;
import lombok.Data;

import java.util.Calendar;

@Data
@Builder
public class DdayCalculationResponse {
    private Long eventId;
    private String title;
    private Long daysRemaining;
    private Long daysPassed;
    private String status; // "UPCOMING", "TODAY", "PASSED"
    private String displayText; // "D-30", "D-Day", "D+10"
}

public void updateCalendarEvent(String accessToken, String eventId, String title, String description) {
    try {
        Calendar service = getCalendarService(accessToken);
        Event event = service.events().get("primary", eventId).execute();

        event.setSummary(title);
        event.setDescription(description);

        service.events().update("primary", eventId, event).execute();
    } catch (Exception e) {
        throw new RuntimeException("구글 캘린더 이벤트 업데이트 실패", e);
    }
}

public void deleteCalendarEvent(String accessToken, String eventId) {
    try {
        Calendar service = getCalendarService(accessToken);
        service.events().delete("primary", eventId).execute();
    } catch (Exception e) {
        throw new RuntimeException("구글 캘린더 이벤트 삭제 실패", e);
    }
}

private Calendar getCalendarService(String accessToken) {
    GoogleCredential credential = new GoogleCredential().setAccessToken(accessToken);
    return new Calendar.Builder(
            new com.google.api.client.http.javanet.NetHttpTransport(),
            new com.google.api.client.json.jackson2.JacksonFactory(),
            credential)
            .setApplicationName("Couple D-day App")
            .build();
}
}
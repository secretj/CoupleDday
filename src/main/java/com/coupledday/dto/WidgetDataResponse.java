package com.coupledday.dto;

import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder
public class WidgetDataResponse {
    private String coupleNickname;
    private Long anniversaryDays;
    private List<UpcomingEvent> upcomingEvents;
    private String todayMessage;

    @Data
    @Builder
    public static class UpcomingEvent {
        private String title;
        private Long daysRemaining;
        private String displayText;
        private String color;
    }
}
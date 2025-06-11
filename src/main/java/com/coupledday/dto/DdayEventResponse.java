package com.coupledday.dto;

import com.coupledday.domain.dday.DdayEvent;
import com.coupledday.domain.dday.DdayType;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDate;

@Data
@Builder
public class DdayEventResponse {
    private Long id;
    private String title;
    private String description;
    private LocalDate targetDate;
    private DdayType type;
    private Integer customInterval;
    private Boolean isRecurring;
    private String color;
    private Long daysRemaining;
    private Long daysPassed;

    public static DdayEventResponse from(DdayEvent event) {
        return DdayEventResponse.builder()
                .id(event.getId())
                .title(event.getTitle())
                .description(event.getDescription())
                .targetDate(event.getTargetDate())
                .type(event.getType())
                .customInterval(event.getCustomInterval())
                .isRecurring(event.getIsRecurring())
                .color(event.getColor())
                // daysRemaining, daysPassed는 서비스에서 계산하여 설정
                .build();
    }
}

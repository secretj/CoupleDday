package com.coupledday.dto;

import com.coupledday.domain.dday.DdayType;
import lombok.Data;
import java.time.LocalDate;

@Data
public class DdayEventRequest {
    private Long coupleId;
    private Long userId;
    private String title;
    private String description;
    private LocalDate targetDate;
    private DdayType type;
    private Integer customInterval;
    private Boolean isRecurring;
    private String color;
}
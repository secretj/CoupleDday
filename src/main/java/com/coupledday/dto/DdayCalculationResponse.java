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
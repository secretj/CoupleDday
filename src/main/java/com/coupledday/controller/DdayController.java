package com.coupledday.controller;

import com.coupledday.domain.dday.DdayEvent; // 추가 필요
import com.coupledday.dto.DdayCalculationResponse; // 추가 필요
import com.coupledday.dto.DdayEventRequest;
import com.coupledday.dto.DdayEventResponse;
import com.coupledday.service.DdayEventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/dday")
@RequiredArgsConstructor
public class DdayController {

    private final DdayEventService ddayEventService;

    @PostMapping
    public ResponseEntity<DdayEventResponse> createDdayEvent(@RequestBody DdayEventRequest request) {
        DdayEvent event = ddayEventService.createDdayEvent(
                request.getCoupleId(),
                request.getUserId(),
                request.getTitle(),
                request.getTargetDate(),
                request.getType(),
                request.getCustomInterval()
        );
        return ResponseEntity.ok(DdayEventResponse.from(event));
    }

    @GetMapping("/couple/{coupleId}")
    public ResponseEntity<List<DdayEventResponse>> getCoupleEvents(@PathVariable Long coupleId) {
        List<DdayEvent> events = ddayEventService.getActiveCoupleEvents(coupleId);
        List<DdayEventResponse> responses = events.stream()
                .map(DdayEventResponse::from)
                .toList();
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/upcoming/{coupleId}")
    public ResponseEntity<List<DdayEventResponse>> getUpcomingEvents(
            @PathVariable Long coupleId,
            @RequestParam(defaultValue = "30") int days) {

        List<DdayEvent> events = ddayEventService.getUpcomingEvents(coupleId, days);
        List<DdayEventResponse> responses = events.stream()
                .map(DdayEventResponse::from)
                .toList();
        return ResponseEntity.ok(responses);
    }

    @PostMapping("/{eventId}/sync-calendar")
    public ResponseEntity<Void> syncWithGoogleCalendar(@PathVariable Long eventId,
                                                       @RequestHeader("Authorization") String token) {
        String accessToken = extractAccessToken(token);
        ddayEventService.syncWithGoogleCalendar(eventId, accessToken);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{eventId}/calculate")
    public ResponseEntity<DdayCalculationResponse> calculateDday(@PathVariable Long eventId) {
        DdayCalculationResponse response = ddayEventService.calculateDday(eventId);
        return ResponseEntity.ok(response);
    }

    private String extractAccessToken(String authHeader) {
        return authHeader.replace("Bearer ", "");
    }
}
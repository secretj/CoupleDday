package com.coupledday.service;

import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
public class DdayCalculationService {

    public Long calculateDaysFromStart(LocalDate startDate, LocalDate targetDate) {
        return ChronoUnit.DAYS.between(startDate, targetDate);
    }

    public Long calculateDaysUntilTarget(LocalDate targetDate) {
        return ChronoUnit.DAYS.between(LocalDate.now(), targetDate);
    }

    public List<LocalDate> generateAnniversaryDates(LocalDate anniversaryDate, int years) {
        List<LocalDate> dates = new ArrayList<>();

        // 100일 단위 기념일 생성
        for (int i = 1; i <= 10; i++) {
            dates.add(anniversaryDate.plusDays(i * 100));
        }

        // 연도별 기념일 생성
        for (int i = 1; i <= years; i++) {
            dates.add(anniversaryDate.plusYears(i));
        }

        return dates;
    }

    public List<LocalDate> generateCustomIntervalDates(LocalDate startDate, int intervalDays, int count) {
        List<LocalDate> dates = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            dates.add(startDate.plusDays(i * intervalDays));
        }
        return dates;
    }
}
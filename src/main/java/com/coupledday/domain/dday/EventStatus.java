package com.coupledday.domain.dday;

import lombok.Getter;

@Getter
public enum EventStatus {
    ACTIVE("활성"),
    COMPLETED("완료"),
    CANCELLED("취소됨");

    private final String description;

    EventStatus(String description) {
        this.description = description;
    }

}
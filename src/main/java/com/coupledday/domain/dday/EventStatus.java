package com.coupledday.domain.dday;

public enum EventStatus {
    ACTIVE("활성"),
    COMPLETED("완료"),
    CANCELLED("취소됨");

    private final String description;

    EventStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
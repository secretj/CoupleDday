package com.coupledday.domain.couple;

public enum CoupleStatus {
    ACTIVE("활성"),
    INACTIVE("비활성"),
    PENDING("대기중"),
    SEPARATED("분리됨");

    private final String description;

    CoupleStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
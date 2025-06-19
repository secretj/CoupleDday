package com.coupledday.domain.dday;

import lombok.Getter;

@Getter
public enum DdayType {
    ANNIVERSARY_FIRST("첫 만남", 0),
    ANNIVERSARY_100("100일", 100),
    ANNIVERSARY_200("200일", 200),
    ANNIVERSARY_300("300일", 300),
    ANNIVERSARY_365("1주년", 365),
    ANNIVERSARY_730("2주년", 730),
    ANNIVERSARY_1095("3주년", 1095),
    CUSTOM("사용자 지정", null);

    private final String description;
    private final Integer defaultDays;

    DdayType(String description, Integer defaultDays) {
        this.description = description;
        this.defaultDays = defaultDays;
    }
}
package com.coupledday.domain.notification;

import lombok.Getter;

@Getter
public enum NotificationType {
    PUSH("푸시 알림"),
    EMAIL("이메일"),
    SMS("문자 메시지");

    private final String description;

    NotificationType(String description) {
        this.description = description;
    }

}
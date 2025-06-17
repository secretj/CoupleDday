package com.coupledday.domain.user;

import lombok.Getter;

@Getter
public enum UserStatus {
    ACTIVE("활성"),
    INACTIVE("비활성"),
    SUSPENDED("정지됨"),
    DELETED("삭제됨");

    private final String description;

    UserStatus(String description) {
        this.description = description;
    }

}
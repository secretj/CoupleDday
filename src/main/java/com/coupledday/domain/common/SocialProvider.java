package com.coupledday.domain.common;

import lombok.Getter;

@Getter
public enum SocialProvider {
    KAKAO("카카오"),
    NAVER("네이버"),
    GOOGLE("구글");

    private final String description;

    SocialProvider(String description) {
        this.description = description;
    }

}

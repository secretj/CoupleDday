package com.coupledday.security.oauth2;

import com.coupledday.domain.common.SocialProvider;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

@Getter
public class CustomOAuth2User implements OAuth2User {
    private final OAuth2User oauth2User;
    private final SocialProvider provider;
    private final String providerId;
    private final String email;
    private final String nickname;
    private final String profileImageUrl;

    public CustomOAuth2User(OAuth2User oauth2User, SocialProvider provider) {
        this.oauth2User = oauth2User;
        this.provider = provider;

        switch (provider) {
            case GOOGLE:
                this.providerId = oauth2User.getAttribute("sub");
                this.email = oauth2User.getAttribute("email");
                this.nickname = oauth2User.getAttribute("name");
                this.profileImageUrl = oauth2User.getAttribute("picture");
                break;

            case KAKAO:
                this.providerId = oauth2User.getAttribute("id").toString();
                Map<String, Object> kakaoAccount = oauth2User.getAttribute("kakao_account");
                Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");
                this.email = (String) kakaoAccount.get("email");
                this.nickname = (String) profile.get("nickname");
                this.profileImageUrl = (String) profile.get("profile_image_url");
                break;

            case NAVER:
                Map<String, Object> response = oauth2User.getAttribute("response");
                this.providerId = (String) response.get("id");
                this.email = (String) response.get("email");
                this.nickname = (String) response.get("name");
                this.profileImageUrl = (String) response.get("profile_image");
                break;

            default:
                throw new IllegalArgumentException("지원하지 않는 소셜 로그인 제공자입니다.");
        }
    }

    @Override
    public Map<String, Object> getAttributes() {
        return oauth2User.getAttributes();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getName() {
        return providerId;
    }
}
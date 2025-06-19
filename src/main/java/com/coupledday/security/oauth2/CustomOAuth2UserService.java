package com.coupledday.security.oauth2;

import com.coupledday.domain.user.Role;
import com.coupledday.domain.common.SocialProvider;
import com.coupledday.domain.user.User;
import com.coupledday.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oauth2User = super.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        SocialProvider provider = getSocialProvider(registrationId);

        CustomOAuth2User customOAuth2User = new CustomOAuth2User(oauth2User, provider);

        // 사용자 정보 저장 또는 업데이트
        saveOrUpdateUser(customOAuth2User);

        return customOAuth2User;
    }

    private SocialProvider getSocialProvider(String registrationId) {
        return switch (registrationId.toLowerCase()) {
            case "google" -> SocialProvider.GOOGLE;
            case "kakao" -> SocialProvider.KAKAO;
            case "naver" -> SocialProvider.NAVER;
            default -> throw new IllegalArgumentException("지원하지 않는 소셜 로그인 제공자: " + registrationId);
        };
    }

    private void saveOrUpdateUser(CustomOAuth2User oauth2User) {
        Optional<User> existingUser = userRepository.findByProviderAndProviderId(
                oauth2User.getProvider(), oauth2User.getProviderId());

        if (existingUser.isPresent()) {
            // 기존 사용자 정보 업데이트
            User user = existingUser.get();
            user.updateProfile(oauth2User.getNickname(), oauth2User.getProfileImageUrl());
            userRepository.save(user);
        } else {
            // 새 사용자 생성
            User newUser = User.builder()
                    .email(oauth2User.getEmail())
                    .nickname(oauth2User.getNickname())
                    .profileImageUrl(oauth2User.getProfileImageUrl())
                    .provider(oauth2User.getProvider())
                    .providerId(oauth2User.getProviderId())
                    .role(Role.USER)
                    .build();
            userRepository.save(newUser);
        }
    }
}
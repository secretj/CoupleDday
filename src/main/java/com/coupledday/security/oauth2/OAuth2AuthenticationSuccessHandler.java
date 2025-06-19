package com.coupledday.security.oauth2;

import com.coupledday.security.JwtTokenProvider;
import com.coupledday.domain.user.User;
import com.coupledday.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtTokenProvider tokenProvider;
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        CustomOAuth2User oauth2User = (CustomOAuth2User) authentication.getPrincipal();

        // 사용자 정보 조회
        User user = userRepository.findByProviderAndProviderId(
                        oauth2User.getProvider(), oauth2User.getProviderId())
                .orElseThrow(() -> new RuntimeException("사용자 정보를 찾을 수 없습니다."));

        // JWT 토큰 생성
        String token = tokenProvider.createToken(user.getEmail(), user.getRole().name());

        // 응답 설정
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        Map<String, Object> result = new HashMap<>();
        result.put("token", token);
        result.put("user", Map.of(
                "id", user.getId(),
                "email", user.getEmail(),
                "nickname", user.getNickname(),
                "profileImageUrl", user.getProfileImageUrl(),
                "provider", user.getProvider().name()
        ));

        response.getWriter().write(objectMapper.writeValueAsString(result));
    }
}
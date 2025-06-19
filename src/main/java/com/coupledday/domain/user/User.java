package com.coupledday.domain.user;

import com.coupledday.domain.common.SocialProvider;
import com.coupledday.domain.dday.DdayEvent;
import com.coupledday.domain.memory.CoupleMemory;
import jakarta.persistence.*;
import jdk.jfr.Description;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "users")
@Getter @Setter @NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Description("소셜로그인 사용자는 비번없음")
    private String password;

    @Column(nullable = false)
    private String nickname;

    private String profileImageUrl;

    @Description("소셜 로그인 관련 필드")
    @Enumerated(EnumType.STRING)
    private SocialProvider provider;

    @Description("소셜 로그인 제공자의 사용자 ID")
    private String providerId;

    private String googleCalendarToken;

    private String googleRefreshToken;

    @Enumerated(EnumType.STRING)
    private UserStatus status = UserStatus.ACTIVE;

    @Enumerated(EnumType.STRING)
    private Role role = Role.USER;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<DdayEvent> ddayEvents;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<CoupleMemory> memories;

    @Builder
    public User(String email, String password, String nickname, String profileImageUrl,
                SocialProvider provider, String providerId, Role role) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.profileImageUrl = profileImageUrl;
        this.provider = provider;
        this.providerId = providerId;
        this.role = role;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public void updateProfile(String nickname, String profileImageUrl) {
        this.nickname = nickname;
        this.profileImageUrl = profileImageUrl;
        this.updatedAt = LocalDateTime.now();
    }

    public String getProviderKey() {
        return provider + "_" + providerId;
    }
}
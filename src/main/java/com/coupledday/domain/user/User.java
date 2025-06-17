package com.coupledday.domain.user;

import com.coupledday.domain.dday.DdayEvent;
import com.coupledday.domain.memory.CoupleMemory;
import jakarta.persistence.*;
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

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String nickname;

    private String profileImageUrl;

    private String googleCalendarToken;

    private String googleRefreshToken;

    @Enumerated(EnumType.STRING)
    private UserStatus status = UserStatus.ACTIVE;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<DdayEvent> ddayEvents;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<CoupleMemory> memories;
}
package com.coupledday.domain.couple;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "couples")
@Getter @Setter @NoArgsConstructor
public class Couple {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long user1Id;

    @Column(nullable = false)
    private Long user2Id;

    @Column(nullable = false)
    private LocalDate anniversaryDate; // 사귄 날짜

    @Enumerated(EnumType.STRING)
    private CoupleStatus status = CoupleStatus.ACTIVE;

    private String coupleCode; // 커플 매칭을 위한 코드

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "couple", cascade = CascadeType.ALL)
    private List<DdayEvent> ddayEvents;

    @OneToMany(mappedBy = "couple", cascade = CascadeType.ALL)
    private List<CoupleMemory> memories;
}
package com.coupledday.domain.memory;

import com.coupledday.domain.couple.Couple;
import com.coupledday.domain.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "couple_memories")
@Getter @Setter @NoArgsConstructor
public class CoupleMemory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "couple_id")
    private Couple couple;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDate memoryDate;

    private String location;

    @OneToMany(mappedBy = "memory", cascade = CascadeType.ALL)
    private List<MemoryMedia> mediaFiles;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public void setCoupleById(Long coupleId) {
        if (coupleId != null) {
            this.couple = new Couple();
            this.couple.setId(coupleId);
        }
    }

    public void setUserById(Long userId) {
        if (userId != null) {
            this.user = new User();
            this.user.setId(userId);
        }
    }
}
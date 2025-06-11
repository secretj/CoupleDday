package com.coupledday.domain.memory;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "memory_media")
@Getter @Setter @NoArgsConstructor
public class MemoryMedia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memory_id")
    private CoupleMemory memory;

    @Column(nullable = false)
    private String fileName;

    @Column(nullable = false)
    private String filePath;

    @Enumerated(EnumType.STRING)
    private MediaType mediaType; // IMAGE, VIDEO

    private Long fileSize;

    private String mimeType;

    private Integer sortOrder;
}
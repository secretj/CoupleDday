package com.coupledday.dto;

import com.coupledday.domain.memory.CoupleMemory;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class MemoryResponse {
    private Long id;
    private String title;
    private String content;
    private LocalDate memoryDate;
    private String location;
    private List<MediaResponse> mediaFiles;
    private LocalDateTime createdAt;

    public static MemoryResponse from(CoupleMemory memory) {
        return MemoryResponse.builder()
                .id(memory.getId())
                .title(memory.getTitle())
                .content(memory.getContent())
                .memoryDate(memory.getMemoryDate())
                .location(memory.getLocation())
                .createdAt(memory.getCreatedAt())
                .build();
    }

    @Data
    @Builder
    public static class MediaResponse {
        private Long id;
        private String fileName;
        private String filePath;
        private String mediaType;
        private Long fileSize;
    }
}
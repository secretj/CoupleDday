package com.coupledday.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class MemoryRequest {
    private Long coupleId;
    private Long userId;
    private String title;
    private String content;
    private LocalDate memoryDate;
    private String location;
}
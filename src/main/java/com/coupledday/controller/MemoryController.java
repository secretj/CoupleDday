package com.coupledday.controller;

import com.coupledday.domain.memory.CoupleMemory;
import com.coupledday.dto.MemoryRequest;
import com.coupledday.dto.MemoryResponse;
import com.coupledday.service.MemoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@RestController
@RequestMapping("/api/memories")
@RequiredArgsConstructor
public class MemoryController {

    private final MemoryService memoryService;

    /**
     * 커플 메모리 생성
     * @param request : MemoryRequest 객체
     * @return ResponseEntity<MemoryResponse>
     */
    @PostMapping
    public ResponseEntity<MemoryResponse> createMemory(@RequestBody MemoryRequest request) {
        CoupleMemory memory = memoryService.createMemory(request);
        return ResponseEntity.ok(MemoryResponse.from(memory));
    }

    /**
     * 사진/영상 업로드
     * @param memoryId : 메모리 ID
     * @param files : 업로드할 파일
     * @return ResponseEntity<MemoryResponse>
     */
    @PostMapping("/{memoryId}/media")
    public ResponseEntity<Void> uploadMedia(@PathVariable Long memoryId,
                                            @RequestParam("files") List<MultipartFile> files) {
        memoryService.uploadMemoryMedia(memoryId, files);
        return ResponseEntity.ok().build();
    }

    /**
     * 커플의 메모리 목록 조회
     * @param coupleId : 커플 ID
     * @return ResponseEntity<List<MemoryResponse>>
     */
    @GetMapping("/couple/{coupleId}")
    public ResponseEntity<List<MemoryResponse>> getCoupleMemories(@PathVariable Long coupleId) {
        List<CoupleMemory> memories = memoryService.getCoupleMemories(coupleId);
        List<MemoryResponse> responses = memories.stream()
                .map(MemoryResponse::from)
                .toList();
        return ResponseEntity.ok(responses);
    }
}
package com.coupledday.controller;

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

    @PostMapping
    public ResponseEntity<MemoryResponse> createMemory(@RequestBody MemoryRequest request) {
        CoupleMemory memory = memoryService.createMemory(request);
        return ResponseEntity.ok(MemoryResponse.from(memory));
    }

    @PostMapping("/{memoryId}/media")
    public ResponseEntity<Void> uploadMedia(@PathVariable Long memoryId,
                                            @RequestParam("files") List<MultipartFile> files) {
        memoryService.uploadMemoryMedia(memoryId, files);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/couple/{coupleId}")
    public ResponseEntity<List<MemoryResponse>> getCoupleMemories(@PathVariable Long coupleId) {
        List<CoupleMemory> memories = memoryService.getCoupleMemories(coupleId);
        List<MemoryResponse> responses = memories.stream()
                .map(MemoryResponse::from)
                .toList();
        return ResponseEntity.ok(responses);
    }
}
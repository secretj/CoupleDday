package com.coupledday.service;

import com.coupledday.domain.memory.CoupleMemory;
import com.coupledday.domain.memory.MemoryMedia;
import com.coupledday.domain.memory.MediaType;
import com.coupledday.dto.MemoryRequest;
import com.coupledday.repository.CoupleMemoryRepository;
import com.coupledday.repository.MemoryMediaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MemoryService {

    private final CoupleMemoryRepository memoryRepository;
    private final MemoryMediaRepository mediaRepository;
    private final FileUploadService fileUploadService;

    public CoupleMemory createMemory(MemoryRequest request) {
        CoupleMemory memory = new CoupleMemory();
        memory.setCoupleById(request.getCoupleId());
        memory.setUserById(request.getUserId());
        memory.setTitle(request.getTitle());
        memory.setContent(request.getContent());
        memory.setMemoryDate(request.getMemoryDate());
        memory.setLocation(request.getLocation());
        memory.setCreatedAt(LocalDateTime.now());

        return memoryRepository.save(memory);
    }

    public void uploadMemoryMedia(Long memoryId, List<MultipartFile> files) {
        CoupleMemory memory = memoryRepository.findById(memoryId)
                .orElseThrow(() -> new RuntimeException("메모리를 찾을 수 없습니다"));

        int sortOrder = 0;
        for (MultipartFile file : files) {
            try {
                String filePath = fileUploadService.uploadFile(file, "memories/" + memoryId);

                MemoryMedia media = new MemoryMedia();
                media.setMemory(memory);
                media.setFileName(file.getOriginalFilename());
                media.setFilePath(filePath);
                media.setFileSize(file.getSize());
                media.setMimeType(file.getContentType());
                media.setSortOrder(sortOrder++);

                if (fileUploadService.isImageFile(file)) {
                    media.setMediaType(MediaType.IMAGE);
                } else if (fileUploadService.isVideoFile(file)) {
                    media.setMediaType(MediaType.VIDEO);
                }

                mediaRepository.save(media);

            } catch (IOException e) {
                throw new RuntimeException("파일 업로드 실패: " + file.getOriginalFilename(), e);
            }
        }
    }

    public List<CoupleMemory> getCoupleMemories(Long coupleId) {
        return memoryRepository.findByCoupleIdOrderByMemoryDateDesc(coupleId);
    }
}
package com.coupledday.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileUploadService {

    @Value("${file.upload.path}")
    private String uploadPath;

    public String uploadFile(MultipartFile file, String directory) throws IOException {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("파일이 비어있습니다.");
        }

        // 파일 이름 생성
        String originalFilename = file.getOriginalFilename();
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String filename = UUID.randomUUID().toString() + extension;

        // 디렉토리 생성
        Path directoryPath = Paths.get(uploadPath, directory);
        if (!Files.exists(directoryPath)) {
            Files.createDirectories(directoryPath);
        }

        // 파일 저장
        Path filePath = directoryPath.resolve(filename);
        Files.copy(file.getInputStream(), filePath);

        return directory + "/" + filename;
    }

    public void deleteFile(String filePath) throws IOException {
        Path path = Paths.get(uploadPath, filePath);
        Files.deleteIfExists(path);
    }

    public boolean isImageFile(MultipartFile file) {
        String contentType = file.getContentType();
        return contentType != null && contentType.startsWith("image/");
    }

    public boolean isVideoFile(MultipartFile file) {
        String contentType = file.getContentType();
        return contentType != null && contentType.startsWith("video/");
    }
}
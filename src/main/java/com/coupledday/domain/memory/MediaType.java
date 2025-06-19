package com.coupledday.domain.memory;

import lombok.Getter;

@Getter
public enum MediaType {
    IMAGE("이미지", new String[]{"jpg", "jpeg", "png", "gif", "webp"}),
    VIDEO("비디오", new String[]{"mp4", "avi", "mov", "wmv", "flv"});

    private final String description;
    private final String[] allowedExtensions;

    MediaType(String description, String[] allowedExtensions) {
        this.description = description;
        this.allowedExtensions = allowedExtensions;
    }

    public boolean isValidExtension(String extension) {
        if (extension == null) return false;
        String ext = extension.toLowerCase();
        for (String allowed : allowedExtensions) {
            if (allowed.equals(ext)) {
                return true;
            }
        }
        return false;
    }
}
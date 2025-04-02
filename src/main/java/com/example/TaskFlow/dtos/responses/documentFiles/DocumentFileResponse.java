package com.example.TaskFlow.dtos.responses.documentFiles;

import com.example.TaskFlow.entities.DocumentFile;

import java.time.LocalDateTime;

public record DocumentFileResponse (
        String id,
        String fileName,
        String fileType,
        Long taskId,
        long size,
        LocalDateTime uploadedAt
) {
    public static DocumentFileResponse fromEntity(DocumentFile file) {
        return new DocumentFileResponse(file.getId().toString(), file.getFileName(), file.getFileType(), file.getTaskId(), file.getSize(), file.getUploadedAt());
    }
}
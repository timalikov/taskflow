package com.example.TaskFlow.dtos.responses;

import com.example.TaskFlow.entities.Task;
import com.example.TaskFlow.enums.TaskStatus;

import java.time.LocalDateTime;

public record TaskResponseDTO(Long id, String title, String description, TaskStatus taskStatus, LocalDateTime createdAt, LocalDateTime updatedAt) {
    public static TaskResponseDTO fromEntity(Task task) {
        return new TaskResponseDTO(task.getId(), task.getTitle(), task.getDescription(), task.getTaskStatus(), task.getCreatedAt(), task.getUpdatedAt());
    }
}

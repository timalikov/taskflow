package com.example.TaskFlow.dtos.responses.tasks;

import com.example.TaskFlow.entities.Project;
import com.example.TaskFlow.entities.Task;
import com.example.TaskFlow.entities.User;
import com.example.TaskFlow.enums.Priority;
import com.example.TaskFlow.enums.TaskStatus;

import java.time.LocalDateTime;

public record UpdateTaskResponse(Long id, String title, String description, TaskStatus status, Priority priority, LocalDateTime deadline, Long userId, Long projectId, LocalDateTime createdAt, LocalDateTime updatedAt) {
    public static UpdateTaskResponse fromEntity(Task task) {
        return new UpdateTaskResponse(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus(),
                task.getPriority(),
                task.getDeadline(),
                task.getUser().getId(),
                task.getProject().getId(),
                task.getCreatedAt(),
                task.getUpdatedAt());
    }
}
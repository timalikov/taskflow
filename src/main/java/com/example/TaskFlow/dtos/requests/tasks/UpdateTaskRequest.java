package com.example.TaskFlow.dtos.requests.tasks;

import com.example.TaskFlow.enums.Priority;
import com.example.TaskFlow.enums.TaskStatus;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public record UpdateTaskRequest(
        @Size(min = 1, max = 255, message = "First name must be between 1 - 255 characters")
        String title,

        @Size(max = 1000, message = "Task description must be no more than 1000 characters")
        String description,

        TaskStatus status,

        Priority priority,

        LocalDateTime deadline,

        Long userId,

        Long projectId
) {}

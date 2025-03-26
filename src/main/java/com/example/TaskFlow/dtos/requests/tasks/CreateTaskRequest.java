package com.example.TaskFlow.dtos.requests.tasks;

import com.example.TaskFlow.enums.Priority;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record CreateTaskRequest(
        @NotBlank(message = "Title is required")
        @Size(min = 1, max = 255, message = "Title must be between 1 - 255 characters")
        String title,

        @Size(max = 1000, message = "Description must be less than 1000 characters")
        String description,

        Priority priority,

        LocalDateTime deadline,

        @NotNull(message = "User ID is required")
        Long userId,

        @NotNull(message = "Project ID is required")
        Long projectId
) {}

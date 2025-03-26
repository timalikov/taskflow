package com.example.TaskFlow.dtos.requests.projects;

import jakarta.validation.constraints.Size;

import java.util.Set;

public record UpdateProjectRequest (
        @Size(min = 1, max = 255, message = "Project name should be between 1 - 255 characters")
        String name,

        @Size(min = 1, max = 1000, message = "Project name should be between 1 - 255 characters")
        String description,

        Set<Long> userIds
) {}

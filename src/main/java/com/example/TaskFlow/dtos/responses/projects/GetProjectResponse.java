package com.example.TaskFlow.dtos.responses.projects;

import com.example.TaskFlow.entities.Project;
import com.example.TaskFlow.entities.User;
import com.example.TaskFlow.enums.ProjectStatus;

import java.time.LocalDateTime;
import java.util.Set;

public record GetProjectResponse(Long id, String name, String description, ProjectStatus status, Set<Long> userIds, LocalDateTime createdAt, LocalDateTime updatedAt) {
    public static GetProjectResponse fromEntity(Project project) {
        return new GetProjectResponse(project.getId() ,project.getName(), project.getDescription(), project.getStatus(), project.getUserIds(), project.getCreatedAt(), project.getUpdatedAt());
    }
}

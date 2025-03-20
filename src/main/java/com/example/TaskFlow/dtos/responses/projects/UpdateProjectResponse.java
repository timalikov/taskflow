package com.example.TaskFlow.dtos.responses.projects;

import com.example.TaskFlow.entities.Project;
import com.example.TaskFlow.enums.ProjectStatus;

import java.time.LocalDateTime;
import java.util.Set;

public record UpdateProjectResponse (Long id, String name, String description, ProjectStatus status, Set<Long> userIds, LocalDateTime createdAt, LocalDateTime updatedAt) {
    public static UpdateProjectResponse fromEntity(Project project) {
        return new UpdateProjectResponse(project.getId() ,project.getName(), project.getDescription(), project.getStatus(), project.getUserIds(), project.getCreatedAt(), project.getUpdatedAt());
    }
}

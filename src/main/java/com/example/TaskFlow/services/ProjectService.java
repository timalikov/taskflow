package com.example.TaskFlow.services;

import com.example.TaskFlow.dtos.requests.projects.CreateProjectRequest;
import com.example.TaskFlow.dtos.requests.projects.UpdateProjectRequest;
import com.example.TaskFlow.entities.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProjectService {
    Page<Project> getAllProjects(Pageable pageable);
    Project getProjectById(Long id);
    Project createProject(CreateProjectRequest request);
    Project updateProject(Long id, UpdateProjectRequest request);
    void deleteProject(Long id);
}

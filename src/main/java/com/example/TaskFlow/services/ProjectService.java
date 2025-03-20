package com.example.TaskFlow.services;

import com.example.TaskFlow.dtos.requests.projects.CreateProjectRequest;
import com.example.TaskFlow.dtos.requests.projects.UpdateProjectRequest;
import com.example.TaskFlow.dtos.responses.projects.CreateProjectResponse;
import com.example.TaskFlow.dtos.responses.projects.GetProjectResponse;
import com.example.TaskFlow.dtos.responses.projects.UpdateProjectResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProjectService {
    Page<GetProjectResponse> getAllProjects(Pageable pageable);
    GetProjectResponse getProjectById(Long id);
    CreateProjectResponse createProject(CreateProjectRequest request);
    UpdateProjectResponse updateProject(Long id, UpdateProjectRequest request);
    void deleteProject(Long id);
}

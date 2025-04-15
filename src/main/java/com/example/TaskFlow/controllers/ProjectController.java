package com.example.TaskFlow.controllers;

import com.example.TaskFlow.dtos.requests.projects.CreateProjectRequest;
import com.example.TaskFlow.dtos.requests.projects.UpdateProjectRequest;
import com.example.TaskFlow.dtos.responses.projects.CreateProjectResponse;
import com.example.TaskFlow.dtos.responses.projects.GetProjectResponse;
import com.example.TaskFlow.dtos.responses.projects.UpdateProjectResponse;
import com.example.TaskFlow.services.ProjectService;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.net.URI;

@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
public class ProjectController {
    private final ProjectService projectService;

    @GetMapping
    public ResponseEntity<Page<GetProjectResponse>> getAllProjects(Pageable pageable) {
        return ResponseEntity.ok(projectService.getAllProjects(pageable)
                        .map(GetProjectResponse::fromEntity)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetProjectResponse> getProjectById(@PathVariable Long id) {
        return ResponseEntity.ok(
                GetProjectResponse.fromEntity(projectService.getProjectById(id))
        );
    }

    @PostMapping
    public ResponseEntity<CreateProjectResponse> createProject(@Valid @RequestBody CreateProjectRequest request) {
        var response = projectService.createProject(request);
        var location = URI.create("/api/projects/%d".formatted(response.getId()));

        return ResponseEntity.created(location).body(
                CreateProjectResponse.fromEntity(response)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<UpdateProjectResponse> updateProject(
            @PathVariable Long id,
            @Valid @RequestBody UpdateProjectRequest request)
    {
        return ResponseEntity.ok(
                UpdateProjectResponse.fromEntity(projectService.updateProject(id, request))
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long id) {
        projectService.deleteProject(id);
        return ResponseEntity.noContent().build();
    }
}

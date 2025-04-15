package com.example.TaskFlow.services.impl;

import com.example.TaskFlow.dtos.requests.projects.CreateProjectRequest;
import com.example.TaskFlow.dtos.requests.projects.UpdateProjectRequest;
import com.example.TaskFlow.entities.Project;
import com.example.TaskFlow.entities.User;
import com.example.TaskFlow.repositories.ProjectRepository;
import com.example.TaskFlow.repositories.UserRepository;
import com.example.TaskFlow.services.ProjectService;
import com.example.TaskFlow.utils.EntityUtils;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    @Override
    public Page<Project> getAllProjects(Pageable pageable) {
        return projectRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    @Override
    public Project getProjectById(Long id) {
        return EntityUtils.getByIdOrThrow(projectRepository, id, "Project");
    }

    @Override
    public Project createProject(CreateProjectRequest request) {
        Set<User> users = new HashSet<>();
        for (var id : request.userIds()) {
            var user = EntityUtils.getByIdOrThrow(userRepository, id, "User");
            users.add(user);
        }

        var project = new Project();
        project.setName(request.name());
        project.setDescription(request.description());
        project.setUsers(users);

        return projectRepository.save(project);
    }

    @Override
    public Project updateProject(Long id, UpdateProjectRequest request) {
        var project = EntityUtils.getByIdOrThrow(projectRepository, id, "Project");
        Set<User> users = new HashSet<>();

        if (request.userIds() != null) {
            for (var userId : request.userIds()) {
                var user = EntityUtils.getByIdOrThrow(userRepository, userId, "User");
                users.add(user);
            }
            project.setUsers(users);
        }

        if (request.name() != null) project.setName(request.name());
        if (request.description() != null) project.setDescription(request.description());

        return projectRepository.save(project);
    }

    @Override
    public void deleteProject(Long id) {
        var project = EntityUtils.getByIdOrThrow(projectRepository, id, "Project");
        projectRepository.delete(project);
    }
}
package com.example.TaskFlow.services;

import com.example.TaskFlow.dtos.requests.projects.CreateProjectRequest;
import com.example.TaskFlow.entities.Project;
import com.example.TaskFlow.entities.User;
import com.example.TaskFlow.repositories.ProjectRepository;
import com.example.TaskFlow.repositories.UserRepository;
import com.example.TaskFlow.services.impl.ProjectServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProjectServiceImplTest {

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ProjectServiceImpl projectService;

    @Captor
    ArgumentCaptor<Project> projectCaptor;

    private final Set<Long> userIds = Set.of(1L, 2L, 3L);


    @Test
    void shouldCreateProject() {
        var request = new CreateProjectRequest(
                "Test project",
                "Test project description",
                userIds
        );

        for (var id: userIds) {
            var user = new User();
            user.setId(id);
            when(userRepository.findById(id)).thenReturn(Optional.of(user));
        }
        when(projectRepository.save(any(Project.class))).then(i -> i.getArgument(0));

        var result = projectService.createProject(request);

        verify(projectRepository).save(projectCaptor.capture());
        var savedProject = projectCaptor.getValue();

        assertNotNull(result);
        assertEquals(request.name(), savedProject.getName());
        assertEquals(request.description(), savedProject.getDescription());
        assertEquals(request.userIds(), savedProject.getUserIds());

        for (Long id : userIds) {
            verify(userRepository).findById(id);
        }
    }

    @Test
    void shouldDeleteProject() {
        var project = new Project();
        project.setId(1L);

        when(projectRepository.findById(1L)).thenReturn(Optional.of(project));

        projectService.deleteProject(project.getId());

        verify(projectRepository).findById(project.getId());
        verify(projectRepository).delete(project);
    }
}

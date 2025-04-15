package com.example.TaskFlow.controllers;

import com.example.TaskFlow.entities.Project;
import com.example.TaskFlow.entities.Task;
import com.example.TaskFlow.entities.User;
import com.example.TaskFlow.enums.Priority;
import com.example.TaskFlow.enums.TaskStatus;
import com.example.TaskFlow.repositories.ProjectRepository;
import com.example.TaskFlow.repositories.TaskRepository;
import com.example.TaskFlow.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class TaskControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private TaskRepository taskRepository;

    private User user;
    private Project project;

    @BeforeEach
    void setup() {
        userRepository.deleteAll();
        projectRepository.deleteAll();

        var newUser = new User(); newUser.setEmail("aleks@mail.com");
        user = userRepository.save(newUser);

        var newProject = new Project(); newProject.setName("Aleks project");
        project = projectRepository.save(newProject);
    }

    @Test
    void shouldCreateTaskAndReturn201() throws Exception {
        String json = """
            {
              "title": "New Task",
              "description": "Integration Test",
              "priority": "HIGH",
              "status": "IN_PROGRESS",
              "deadline": "2025-12-01T23:59:59",
              "userId": %d,
              "projectId": %d
            }
            """.formatted(user.getId(), project.getId());

        mockMvc.perform(post("/api/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("New Task"))
                .andExpect(jsonPath("$.priority").value("HIGH"));
    }

    @Test
    void shouldGetPaginatedTasksFilteredByStatusAndPriority() throws Exception {
        var task = new Task();
        task.setTitle("Test Task");
        task.setDescription("desc");
        task.setPriority(Priority.HIGH);
        task.setStatus(TaskStatus.IN_PROGRESS);
        task.setUser(user);
        task.setProject(project);
        task.setDeadline(LocalDateTime.now().plusDays(1));
        taskRepository.save(task);

        mockMvc.perform(get("/api/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(1))
                .andExpect(jsonPath("$.content[0].title").value("Test Task"));
    }
}

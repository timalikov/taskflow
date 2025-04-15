package com.example.TaskFlow.services;

import com.example.TaskFlow.dtos.requests.tasks.CreateTaskRequest;
import com.example.TaskFlow.dtos.requests.tasks.UpdateTaskRequest;
import com.example.TaskFlow.entities.Project;
import com.example.TaskFlow.entities.Task;
import com.example.TaskFlow.entities.User;
import com.example.TaskFlow.enums.Priority;
import com.example.TaskFlow.enums.TaskStatus;
import com.example.TaskFlow.exceptions.ResourceNotFoundException;
import com.example.TaskFlow.repositories.ProjectRepository;
import com.example.TaskFlow.repositories.TaskRepository;
import com.example.TaskFlow.repositories.UserRepository;
import com.example.TaskFlow.services.impl.TaskServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceImplTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ProjectRepository projectRepository;

    @InjectMocks
    private TaskServiceImpl taskService;

    @Captor
    ArgumentCaptor<Task> taskCaptor;

    private User user;
    private Project project;
    private Task task;

    private final Long unknownResourceId = 999L;
    private final LocalDateTime taskDeadline = LocalDateTime.of(2025, 5, 10, 12, 0);
    private final String taskTitle = "Test task";
    private final String taskDescription = "Test task description";

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(10L);
        user.setFirstName("Test user");

        project = new Project();
        project.setId(10L);
        project.setName("Test project");

        task = new Task();
        task.setId(1L);
        task.setTitle(taskTitle);
        task.setDescription(taskDescription);
        task.setUser(user);
        task.setProject(project);
    }

    @Test
    void shouldCreateTaskFromRequest() {
        var request = createTestTaskRequest(user.getId(), project.getId());

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(projectRepository.findById(project.getId())).thenReturn(Optional.of(project));
        when(taskRepository.save(any(Task.class))).thenAnswer(i -> i.getArgument(0));

        var result = taskService.createTask(request);

        verify(taskRepository).save(taskCaptor.capture());
        var savedTask = taskCaptor.getValue();

        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(request.title(), savedTask.getTitle()),
                () -> assertEquals(request.description(), savedTask.getDescription()),
                () -> assertEquals(request.priority(), savedTask.getPriority()),
                () -> assertEquals(request.deadline(), savedTask.getDeadline()),
                () -> assertEquals(user.getId(), savedTask.getUser().getId()),
                () -> assertEquals(project.getId(), savedTask.getProject().getId())
        );
    }

    @Test
    void shouldThrowExceptionWhenUserNotFoundWhenCreating() {
        var request = createTestTaskRequest(unknownResourceId, project.getId());

        when(userRepository.findById(request.userId())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> taskService.createTask(request));

        verify(taskRepository, never()).save(any());
    }

    @Test
    void shouldThrowExceptionWhenProjectNotFoundWhenCreating() {
        var request = createTestTaskRequest(user.getId(), unknownResourceId);

        assertThrows(ResourceNotFoundException.class, () -> taskService.createTask(request));

        verify(taskRepository, never()).save(any());
    }

    @Test
    void shouldReturnAllTasksPaginated() {
        var task1 = new Task();
        var task2 = new Task();
        task1.setId(1L);
        task1.setTitle(taskTitle + " 1");
        task2.setId(2L);
        task2.setTitle(taskTitle + " 2");

        var tasks = List.of(task1, task2);
        Pageable pageable = PageRequest.of(0 , 2);
        var taskPage = new PageImpl<>(tasks, pageable, tasks.size());

        when(taskRepository.findAll(pageable)).thenReturn(taskPage);

        var result = taskService.getAllTasks(pageable);

        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(2, result.getContent().size()),
                () -> assertEquals(tasks, result.getContent()),
                () -> assertEquals(0, result.getNumber()),
                () -> assertEquals(2, result.getSize()),
                () -> assertEquals(1, result.getTotalPages()),
                () -> assertEquals(2, result.getTotalElements())
        );

        verify(taskRepository).findAll(pageable);

    }

    @Test
    void shouldReturnTaskById() {
        when(taskRepository.findById(task.getId())).thenReturn(Optional.of(task));

        var result = taskService.getTaskById(task.getId());

        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(task.getId(), result.getId()),
                () -> assertEquals(task.getTitle(), result.getTitle())
        );

        verify(taskRepository).findById(task.getId());
    }

    @Test
    void shouldThrowExceptionWhenTaskNotFound() {
        when(taskRepository.findById(unknownResourceId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> taskService.getTaskById(unknownResourceId));

        verify(taskRepository).findById(unknownResourceId);
    }

    @Test
    void shouldUpdateTaskFromRequest() {
        var request = createUpdateTaskRequest(user.getId(), project.getId());

        when(taskRepository.findById(task.getId())).thenReturn(Optional.of(task));
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(projectRepository.findById(project.getId())).thenReturn(Optional.of(project));
        when(taskRepository.save(any(Task.class))).thenAnswer(i -> i.getArgument(0));

        var result = taskService.updateTask(task.getId(), request);

        verify(taskRepository).save(taskCaptor.capture());
        var updatedTask = taskCaptor.getValue();

        assertAll(
            () -> assertNotNull(result),
            () -> assertEquals(result.getId(), updatedTask.getId()),
            () -> assertEquals(result.getTitle(), updatedTask.getTitle()),
            () -> assertEquals(result.getStatus(), updatedTask.getStatus()),
            () -> assertEquals(result.getPriority(), updatedTask.getPriority()),
            () -> assertEquals(result.getDeadline(), updatedTask.getDeadline()),
            () -> assertEquals(result.getUser().getId(), updatedTask.getUser().getId()),
            () -> assertEquals(result.getProject().getId(), updatedTask.getProject().getId())
        );
    }

    @Test
    void shouldThrowExceptionWhenTaskNotFoundWhenUpdating() {
        var request = createUpdateTaskRequest(user.getId(), project.getId());

        when(taskRepository.findById(unknownResourceId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> taskService.updateTask(unknownResourceId, request));

        verify(taskRepository).findById(unknownResourceId);
    }

    @Test
    void shouldDeleteTask() {
        when(taskRepository.findById(task.getId())).thenReturn(Optional.of(task));

        taskService.deleteTask(task.getId());

        verify(taskRepository).findById(task.getId());
        verify(taskRepository).delete(task);
    }

    @Test
    void shouldThrowExceptionWhenDeletingNonExistingTask() {
        when(taskRepository.findById(unknownResourceId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> taskService.deleteTask(unknownResourceId));

        verify(taskRepository).findById(unknownResourceId);
        verify(taskRepository, never()).delete(any());
    }

    @Test
    void shouldFilterTasksByStatusAndPriority() {
        var status = TaskStatus.IN_PROGRESS;
        var priority = Priority.HIGH;

        var task1 = new Task(); task1.setStatus(status); task1.setPriority(priority); task1.setUser(user);
        var task2 = new Task(); task2.setStatus(status); task2.setPriority(priority); task2.setUser(user);
        var task3 = new Task(); task3.setStatus(status); task3.setPriority(Priority.MEDIUM); task3.setUser(user);
        var task4 = new Task(); task4.setStatus(TaskStatus.DONE); task4.setPriority(priority); task4.setUser(user);

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(taskRepository.findAllByUser(user)).thenReturn(List.of(task1, task2, task3, task4));

        var result = taskService.getTasksFiltered(user.getId(), status.toString(), priority.toString());

        assertAll(
                () -> assertEquals(2, result.size()),
                () -> assertTrue(result.contains(task1)),
                () -> assertTrue(result.contains(task2)),
                () -> assertFalse(result.contains(task3)),
                () -> assertFalse(result.contains(task4))
        );
    }

    private CreateTaskRequest createTestTaskRequest(Long userId, Long projectId) {
        return new CreateTaskRequest(
                taskTitle,
                taskDescription,
                Priority.HIGH,
                taskDeadline,
                userId,
                projectId
        );
    }

    private UpdateTaskRequest createUpdateTaskRequest(Long userId, Long projectId) {
        return new UpdateTaskRequest(
                "Updated %s".formatted(taskTitle),
                "Updated %s".formatted(taskDescription),
                TaskStatus.IN_PROGRESS,
                Priority.HIGH,
                taskDeadline,
                userId,
                projectId
        );
    }
}
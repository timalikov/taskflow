package com.example.TaskFlow.services.impl;

import com.example.TaskFlow.dtos.requests.tasks.CreateTaskRequest;
import com.example.TaskFlow.dtos.requests.tasks.UpdateTaskRequest;
import com.example.TaskFlow.entities.Task;
import com.example.TaskFlow.repositories.ProjectRepository;
import com.example.TaskFlow.repositories.TaskRepository;
import com.example.TaskFlow.repositories.UserRepository;
import com.example.TaskFlow.services.TaskService;
import com.example.TaskFlow.utils.EntityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.TaskFlow.utils.EntityUtils.getByIdOrThrow;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;

    @Override
    public Task createTask(CreateTaskRequest request) {
        var user = getByIdOrThrow(userRepository, request.userId(), "User");
        var project = getByIdOrThrow(projectRepository, request.projectId(), "Project");

        var task = new Task();
        task.setTitle(request.title());
        task.setDescription(request.description());
        task.setPriority(request.priority());
        task.setDeadline(request.deadline());
        task.setUser(user);
        task.setProject(project);
        return taskRepository.save(task);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<Task> getAllTasks(Pageable pageable) {
        return taskRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    @Override
    public Task getTaskById(Long id) {
        return getByIdOrThrow(taskRepository, id, "Task");
    }

    @Override
    public Task updateTask(Long id, UpdateTaskRequest request) {
        var task = getByIdOrThrow(taskRepository, id, "Task");

        if (request.title() != null) task.setTitle(request.title());
        if (request.description() != null) task.setDescription(request.description());
        if (request.status() != null) task.setStatus(request.status());
        if (request.priority() != null) task.setPriority(request.priority());
        if (request.deadline() != null) task.setDeadline(request.deadline());
        if (request.userId() != null) task.setUser(getByIdOrThrow(userRepository, request.userId(), "User"));
        if (request.projectId() != null) task.setProject(getByIdOrThrow(projectRepository, request.projectId(), "Project"));

        return taskRepository.save(task);
    }

    @Override
    public void deleteTask(Long id) {
        taskRepository.delete(getByIdOrThrow(taskRepository, id, "Task"));
    }

    @Override
    public List<Task> getTasksFiltered(Long userId, String status, String priority) {
        var user = getByIdOrThrow(userRepository, userId, "User");

        return taskRepository.findAllByUser(user).stream()
                .filter(task -> status == null || task.getStatus().name().equals(status))
                .filter(task -> priority == null || task.getPriority().name().equals(priority))
                .toList();
    }
}

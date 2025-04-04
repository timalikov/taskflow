package com.example.TaskFlow.services.impl;

import com.example.TaskFlow.dtos.requests.tasks.CreateTaskRequest;
import com.example.TaskFlow.dtos.requests.tasks.UpdateTaskRequest;
import com.example.TaskFlow.entities.Task;
import com.example.TaskFlow.repositories.ProjectRepository;
import com.example.TaskFlow.repositories.TaskRepository;
import com.example.TaskFlow.repositories.UserRespository;
import com.example.TaskFlow.services.TaskService;
import com.example.TaskFlow.utils.EntityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final UserRespository userRespository;
    private final ProjectRepository projectRepository;

    @Override
    public Task createTask(CreateTaskRequest request) {
        var user = EntityUtils.getByIdOrThrow(userRespository, request.userId(), "User");
        var project = EntityUtils.getByIdOrThrow(projectRepository, request.projectId(), "Project");

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
        return EntityUtils.getByIdOrThrow(taskRepository, id, "Task");
    }

    @Override
    public Task updateTask(Long id, UpdateTaskRequest request) {
        var task = EntityUtils.getByIdOrThrow(taskRepository, id, "Task");

        if (request.title() != null) task.setTitle(request.title());
        if (request.description() != null) task.setDescription(request.description());
        if (request.status() != null) task.setStatus(request.status());
        if (request.priority() != null) task.setPriority(request.priority());
        if (request.deadline() != null) task.setDeadline(request.deadline());
        if (request.userId() != null) task.setUser(EntityUtils.getByIdOrThrow(userRespository, request.userId(), "User"));
        if (request.projectId() != null) task.setProject(EntityUtils.getByIdOrThrow(projectRepository, request.projectId(), "Project"));

        return taskRepository.save(task);
    }

    @Override
    public void deleteTask(Long id) {
        taskRepository.delete(EntityUtils.getByIdOrThrow(taskRepository, id, "Task"));
    }
}

package com.example.TaskFlow.services.impl;

import com.example.TaskFlow.dtos.requests.tasks.CreateTaskRequest;
import com.example.TaskFlow.dtos.requests.tasks.UpdateTaskRequest;
import com.example.TaskFlow.dtos.responses.tasks.CreateTaskResponse;
import com.example.TaskFlow.dtos.responses.tasks.GetTaskResponse;
import com.example.TaskFlow.dtos.responses.tasks.UpdateTaskResponse;
import com.example.TaskFlow.entities.Project;
import com.example.TaskFlow.entities.Task;
import com.example.TaskFlow.entities.User;
import com.example.TaskFlow.repositories.ProjectRepository;
import com.example.TaskFlow.repositories.TaskRepository;
import com.example.TaskFlow.repositories.UserRespository;
import com.example.TaskFlow.services.TaskService;
import com.example.TaskFlow.shared.EntityUtils;
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

    @Transactional
    @Override
    public CreateTaskResponse createTask(CreateTaskRequest request) {
        User user = EntityUtils.getByIdOrThrow(userRespository, request.userId(), "User");
        Project project = EntityUtils.getByIdOrThrow(projectRepository, request.projectId(), "Project");

        Task task = new Task();
        task.setTitle(request.title());
        task.setDescription(request.description());
        task.setPriority(request.priority());
        task.setDeadline(request.deadline());
        task.setUser(user);
        task.setProject(project);
        return CreateTaskResponse.fromEntity(taskRepository.save(task));
    }

    @Transactional(readOnly = true)
    @Override
    public Page<GetTaskResponse> getAllTasks(Pageable pageable) {
        return taskRepository.findAll(pageable).map(GetTaskResponse::fromEntity);
    }

    @Transactional(readOnly = true)
    @Override
    public GetTaskResponse getTaskById(Long id) {
        Task task = EntityUtils.getByIdOrThrow(taskRepository, id, "Task");
        return GetTaskResponse.fromEntity(task);
    }

    @Transactional
    @Override
    public UpdateTaskResponse updateTask(Long id, UpdateTaskRequest request) {
        Task task = EntityUtils.getByIdOrThrow(taskRepository, id, "Task");

        if (request.title() != null) task.setTitle(request.title());
        if (request.description() != null) task.setDescription(request.description());
        if (request.status() != null) task.setStatus(request.status());
        if (request.priority() != null) task.setPriority(request.priority());
        if (request.deadline() != null) task.setDeadline(request.deadline());
        if (request.userId() != null) task.setUser(EntityUtils.getByIdOrThrow(userRespository, request.userId(), "User"));
        if (request.projectId() != null) task.setProject(EntityUtils.getByIdOrThrow(projectRepository, request.projectId(), "Project"));

        return UpdateTaskResponse.fromEntity(taskRepository.save(task));
    }

    @Override
    public void deleteTask(Long id) {
        taskRepository.delete(EntityUtils.getByIdOrThrow(taskRepository, id, "Task"));
    }
}

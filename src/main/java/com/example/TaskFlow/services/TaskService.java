package com.example.TaskFlow.services;

import com.example.TaskFlow.dtos.requests.CreateTaskRequest;
import com.example.TaskFlow.dtos.requests.UpdateTaskRequest;
import com.example.TaskFlow.dtos.responses.CreateTaskResponse;
import com.example.TaskFlow.dtos.responses.GetTaskResponse;
import com.example.TaskFlow.dtos.responses.UpdateTaskResponse;
import com.example.TaskFlow.entities.Task;
import com.example.TaskFlow.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class TaskService {
    private Long id = 0L;
    private Map<Long, Task> tasks = new HashMap<>();

    public CreateTaskResponse createTask(CreateTaskRequest request) {
        Task task = new Task();
        task.setId(id);
        task.setTitle(request.title());
        task.setDescription(request.description());
        tasks.put(id, task);
        id++;
        return CreateTaskResponse.fromEntity(task);
    }

    public List<GetTaskResponse> getAllTasks() {
        return tasks.values().stream()
                .map(GetTaskResponse::fromEntity)
                .toList();
    }

    public GetTaskResponse getTaskById(Long id) {
        return GetTaskResponse.fromEntity(getTaskOrThrow(id));
    }

    public UpdateTaskResponse updateTask(Long id, UpdateTaskRequest request) {
        Task task = getTaskOrThrow(id);

        if (request.title() != null) task.setTitle(request.title());
        if (request.description() != null) task.setDescription(request.description());
        if (request.taskStatus() != null) task.setTaskStatus(request.taskStatus());

        if (request.title() != null || request.description() != null || request.taskStatus() != null) {
            task.setUpdatedAt(LocalDateTime.now());
        }

        return UpdateTaskResponse.fromEntity(task);
    }

    public void deleteTask(Long id) {
        tasks.remove(getTaskOrThrow(id).getId());
    }

    private Task getTaskOrThrow(Long id) {
        return Optional.ofNullable(tasks.get(id))
                .orElseThrow(() -> new ResourceNotFoundException("Task with ID " + id + " not found"));
    }

}

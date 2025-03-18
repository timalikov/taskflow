package com.example.TaskFlow.services;

import com.example.TaskFlow.dtos.requests.TaskRequest;
import com.example.TaskFlow.dtos.responses.TaskResponse;
import com.example.TaskFlow.entities.Task;
import com.example.TaskFlow.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TaskService {
    private Long id = 0L;
    private Map<Long, Task> tasks = new HashMap<>();

    public TaskResponse createTask(TaskRequest request) {
        Task task = new Task();
        task.setId(id);
        task.setTitle(request.title());
        task.setDescription(request.description());
        tasks.put(id, task);
        id++;
        return TaskResponse.fromEntity(task);
    }

    public List<TaskResponse> getAllTasks() {
        return tasks.values().stream()
                .map(TaskResponse::fromEntity)
                .toList();
    }

    public TaskResponse getTaskById(Long id) {
        Task task = tasks.get(id);
        if (task == null) {
            throw new ResourceNotFoundException("Task with id: " + id + " not found");
        }
        return TaskResponse.fromEntity(task);
    }

    public TaskResponse updateTask(Long id, TaskRequest request) {
        Task task = tasks.get(id);

        if (task == null) {
            throw new ResourceNotFoundException("Task with ID " + id + " not found");
        }

        if (request.title() != null) task.setTitle(request.title());
        if (request.description() != null) task.setDescription(request.description());
        if (request.taskStatus() != null) task.setTaskStatus(request.taskStatus());

        if (request.title() != null || request.description() != null || request.taskStatus() != null) {
            task.setUpdatedAt(LocalDateTime.now());
        }

        return TaskResponse.fromEntity(task);
    }

    public boolean deleteTask(Long id) {
        if (tasks.get(id) == null) {
            throw new ResourceNotFoundException("Task with id: " + id + " not found");
        }
        tasks.remove(id);
        return true;
    }
}

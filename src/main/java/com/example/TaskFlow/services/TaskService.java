package com.example.TaskFlow.services;

import com.example.TaskFlow.dtos.requests.tasks.CreateTaskRequest;
import com.example.TaskFlow.dtos.requests.tasks.UpdateTaskRequest;
import com.example.TaskFlow.entities.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TaskService {
    Task createTask(CreateTaskRequest request);
    Page<Task> getAllTasks(Pageable pageable);
    Task getTaskById(Long id);
    Task updateTask(Long id, UpdateTaskRequest request);
    void deleteTask(Long id);
}

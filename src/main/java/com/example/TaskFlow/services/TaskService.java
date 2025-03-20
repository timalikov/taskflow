package com.example.TaskFlow.services;

import com.example.TaskFlow.dtos.requests.tasks.CreateTaskRequest;
import com.example.TaskFlow.dtos.requests.tasks.UpdateTaskRequest;
import com.example.TaskFlow.dtos.responses.tasks.CreateTaskResponse;
import com.example.TaskFlow.dtos.responses.tasks.GetTaskResponse;
import com.example.TaskFlow.dtos.responses.tasks.UpdateTaskResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TaskService {
    CreateTaskResponse createTask(CreateTaskRequest request);
    Page<GetTaskResponse> getAllTasks(Pageable pageable);
    GetTaskResponse getTaskById(Long id);
    UpdateTaskResponse updateTask(Long id, UpdateTaskRequest request);
    void deleteTask(Long id);
}

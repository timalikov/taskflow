package com.example.TaskFlow.services;

import com.example.TaskFlow.dtos.responses.TaskResponseDTO;
import com.example.TaskFlow.entities.Task;
import com.example.TaskFlow.enums.TaskStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TaskService {
    private Long id = 0L;
    private Map<Long, Task> tasks = new HashMap<>();

    public TaskResponseDTO createTask(String title, String description) {
        Task task = new Task(id, title, description);
        tasks.put(id, task);
        id++;
        return TaskResponseDTO.fromEntity(task);
    }

    public List<TaskResponseDTO> getAllTasks() {
        return tasks.values().stream()
                .map(TaskResponseDTO::fromEntity)
                .toList();
    }

    public TaskResponseDTO getTaskById(Long id) {
        return TaskResponseDTO.fromEntity(tasks.get(id));
    }

    public TaskResponseDTO updateTask(Long id, String title, String description, TaskStatus taskStatus) {
        Task task = tasks.get(id);
        if (title != null) task.setTitle(title);
        if (description != null) task.setDescription(description);
        if (taskStatus != null) task.setTaskStatus(taskStatus);
        if (title != null || description != null || taskStatus != null) task.setUpdatedAt(LocalDateTime.now());
        return TaskResponseDTO.fromEntity(task);
    }

    public boolean deleteTask(Long id) {
        return tasks.remove(id) != null;
    }
}

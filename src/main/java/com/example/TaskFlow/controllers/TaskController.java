package com.example.TaskFlow.controllers;

import com.example.TaskFlow.dtos.requests.CreateTaskRequest;
import com.example.TaskFlow.dtos.requests.UpdateTaskRequest;
import com.example.TaskFlow.dtos.responses.CreateTaskResponse;
import com.example.TaskFlow.dtos.responses.GetTaskResponse;
import com.example.TaskFlow.dtos.responses.UpdateTaskResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import com.example.TaskFlow.services.TaskService;

import java.util.List;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @PostMapping
    public ResponseEntity<CreateTaskResponse> createTask(@RequestBody CreateTaskRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(taskService.createTask(request));
    }

    @GetMapping
    public ResponseEntity<List<GetTaskResponse>> getAllTasks() {
        return ResponseEntity.ok(taskService.getAllTasks());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetTaskResponse> getTaskById(@PathVariable Long id) {
        return ResponseEntity.ok(taskService.getTaskById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UpdateTaskResponse> updateTask(@PathVariable Long id, @RequestBody UpdateTaskRequest request) {
        return ResponseEntity.ok(taskService.updateTask(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }
}

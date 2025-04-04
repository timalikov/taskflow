package com.example.TaskFlow.controllers;

import com.example.TaskFlow.dtos.requests.tasks.CreateTaskRequest;
import com.example.TaskFlow.dtos.requests.tasks.UpdateTaskRequest;
import com.example.TaskFlow.dtos.responses.tasks.CreateTaskResponse;
import com.example.TaskFlow.dtos.responses.tasks.GetTaskResponse;
import com.example.TaskFlow.dtos.responses.tasks.UpdateTaskResponse;
import com.example.TaskFlow.services.TaskService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.net.URI;


@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @PostMapping
    public ResponseEntity<CreateTaskResponse> createTask(@Valid @RequestBody CreateTaskRequest request) {
        var response = taskService.createTask(request);
        var location = URI.create("/api/tasks/%d".formatted(response.getId()));

        return ResponseEntity.created(location)
                .body(CreateTaskResponse.fromEntity(response));
    }

    @GetMapping
    public ResponseEntity<Page<GetTaskResponse>> getAllTasks(Pageable pageable) {
        return ResponseEntity.ok(taskService.getAllTasks(pageable)
                .map(GetTaskResponse::fromEntity));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetTaskResponse> getTaskById(@PathVariable Long id) {
        return ResponseEntity.ok(GetTaskResponse.fromEntity(taskService.getTaskById(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UpdateTaskResponse> updateTask(
            @PathVariable Long id,
            @Valid @RequestBody UpdateTaskRequest request)
    {
        return ResponseEntity.ok(UpdateTaskResponse.fromEntity(taskService.updateTask(id, request)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }
}

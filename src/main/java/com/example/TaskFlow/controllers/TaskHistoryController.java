package com.example.TaskFlow.controllers;

import com.example.TaskFlow.dtos.responses.taskHistories.TaskHistoryResponse;
import com.example.TaskFlow.services.TaskHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/task-history")
@RequiredArgsConstructor
public class TaskHistoryController {
    private final TaskHistoryService taskHistoryService;

    @GetMapping("/{id}")
    public ResponseEntity<List<TaskHistoryResponse>> getTaskHistory(@PathVariable Long id) {
        return ResponseEntity.ok(taskHistoryService.getAllTaskHistory(id)
                .stream().map(TaskHistoryResponse::fromEntity)
                .toList()
        );
    }
}

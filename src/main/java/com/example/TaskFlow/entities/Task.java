package com.example.TaskFlow.entities;

import com.example.TaskFlow.enums.TaskStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Task {
    private Long id;
    private String title;
    private String description;
    private TaskStatus taskStatus = TaskStatus.TODO;
    private final LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt = LocalDateTime.now();
}

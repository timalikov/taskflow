package com.example.TaskFlow.entities;

import com.example.TaskFlow.enums.TaskStatus;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Getter
@Setter
public class Task {
    private final Long id;
    @NonNull
    private String title;
    @NonNull
    private String description;
    private TaskStatus taskStatus = TaskStatus.TODO;
    private final LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt = LocalDateTime.now();
}

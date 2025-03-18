package com.example.TaskFlow.dtos.requests;

import com.example.TaskFlow.enums.TaskStatus;

public record UpdateTaskRequest(String title, String description, TaskStatus taskStatus) {
}

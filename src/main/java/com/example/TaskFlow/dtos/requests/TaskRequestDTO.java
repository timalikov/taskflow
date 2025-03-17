package com.example.TaskFlow.dtos.requests;


import com.example.TaskFlow.enums.TaskStatus;

public record TaskRequestDTO(String title, String description, TaskStatus taskStatus) {
}

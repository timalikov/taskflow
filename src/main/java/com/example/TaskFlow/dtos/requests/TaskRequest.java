package com.example.TaskFlow.dtos.requests;


import com.example.TaskFlow.enums.TaskStatus;

public record TaskRequest(String title, String description, TaskStatus taskStatus) {
}

package com.example.TaskFlow.dtos.responses.users;

import com.example.TaskFlow.entities.User;

import java.time.LocalDateTime;

public record GetUserResponse(Long id, String firstName, String lastName, String email, boolean active, LocalDateTime createdAt, LocalDateTime updatedAt) {
    public static GetUserResponse fromEntity(User user) {
        return new GetUserResponse(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(), user.isActive(), user.getCreatedAt(), user.getUpdatedAt());
    }
}

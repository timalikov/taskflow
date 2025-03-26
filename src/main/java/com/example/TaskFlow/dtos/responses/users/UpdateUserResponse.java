package com.example.TaskFlow.dtos.responses.users;

import com.example.TaskFlow.entities.User;

import java.time.LocalDateTime;

public record UpdateUserResponse(Long id, String firstName, String lastName, String email, boolean active, LocalDateTime createdAt, LocalDateTime updatedAt) {
    public static UpdateUserResponse fromEntity(User user) {
        return new UpdateUserResponse(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(), user.isActive(), user.getCreatedAt(), user.getUpdatedAt());
    }
}

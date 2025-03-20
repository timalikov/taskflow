package com.example.TaskFlow.dtos.responses.users;

import com.example.TaskFlow.entities.User;

import java.time.LocalDateTime;

public record CreateUserResponse(Long id, String firstName, String lastName, String email, boolean active, LocalDateTime createdAt, LocalDateTime updatedAt) {
    public static CreateUserResponse fromEntity(User user) {
        return new CreateUserResponse(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(), user.isActive(), user.getCreatedAt(), user.getUpdatedAt());
    }
}

package com.example.TaskFlow.dtos.requests.users;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateUserRequest(
        @Size(min = 1, max = 255, message = "First name must be between 1 - 255 characters")
        String firstName,

        @Size(min = 1, max = 255, message = "Last name must be between 1 - 255 characters")
        String lastName,

        @Email(message = "Email should be valid")
        String email
) {
}

package com.example.TaskFlow.exceptions;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ApiErrorResponse {
    private int status;
    private String message;
    private String error;
    private LocalDateTime timestamp;

    public ApiErrorResponse(int status, String message, String error, LocalDateTime timestamp) {
        this.status = status;
        this.message = message;
        this.error = error;
        this.timestamp = timestamp;
    }

    public static ApiErrorResponse buildApiErrorResponse(int status, String message, String error) {
        return new ApiErrorResponse(status, message, error, LocalDateTime.now());
    }
}

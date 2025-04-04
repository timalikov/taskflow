package com.example.TaskFlow.exceptions.handlers;

import com.example.TaskFlow.exceptions.ApiErrorResponse;
import com.example.TaskFlow.exceptions.ResourceNotFoundException;
import com.example.TaskFlow.exceptions.BadRequestException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleNotFound(ResourceNotFoundException ex) {
        Map<String, Object> error = new LinkedHashMap<>();
        error.put("status", HttpStatus.NOT_FOUND.value());
        error.put("message", ex.getMessage());
        error.put("error", ex.getClass().getSimpleName());
        error.put("timestamp", LocalDateTime.now());

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiErrorResponse> handleBadRequest(BadRequestException ex) {

        HttpStatus status = HttpStatus.BAD_REQUEST;
        ApiErrorResponse error = ApiErrorResponse.buildApiErrorResponse(
                status.value(),
                ex.getMessage(),
                ex.getClass().getSimpleName()
        );
        return new ResponseEntity<>(error, status);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleOtherExceptions(Exception ex) {

        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        ApiErrorResponse error = ApiErrorResponse.buildApiErrorResponse(
                status.value(),
                "Unexpected error occurred",
                ex.getClass().getSimpleName()
        );
        return new ResponseEntity<>(error, status);
    }
}

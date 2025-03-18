package com.example.TaskFlow.exceptions.handlers;

import com.example.TaskFlow.exceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(ResourceNotFoundException ex) {
        ErrorResponse error = ErrorResponse.create(ex, HttpStatus.NOT_FOUND, "The requested resource was not found.");
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
}

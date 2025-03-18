package com.example.TaskFlow.exceptions.handlers;

import com.example.TaskFlow.exceptions.ApiErrorResponse;
import com.example.TaskFlow.exceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleNotFound(ResourceNotFoundException ex) {
        ApiErrorResponse error = ApiErrorResponse.buildApiErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage(), ex.getClass().getSimpleName());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
}

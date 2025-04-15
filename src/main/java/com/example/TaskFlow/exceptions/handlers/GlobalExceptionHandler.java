package com.example.TaskFlow.exceptions.handlers;

import com.example.TaskFlow.exceptions.ApiErrorResponse;
import com.example.TaskFlow.exceptions.ResourceNotFoundException;
import com.example.TaskFlow.exceptions.BadRequestException;

import jakarta.validation.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleResourceNotFound(ResourceNotFoundException ex) {

        HttpStatus status = HttpStatus.NOT_FOUND;
        ApiErrorResponse error = ApiErrorResponse.buildApiErrorResponse(
                status.value(),
                ex.getMessage(),
                ex.getClass().getSimpleName()
        );

        return new ResponseEntity<>(error, status);
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

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleValidationError(MethodArgumentNotValidException ex) {

        HttpStatus status = HttpStatus.BAD_REQUEST;
        ApiErrorResponse error = ApiErrorResponse.buildApiErrorResponse(
                status.value(),
                ex.getMessage(),
                ex.getClass().getSimpleName()
        );

        return new ResponseEntity<>(error, status);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ApiErrorResponse> handleValidationException(ValidationException ex) {

        HttpStatus status = HttpStatus.CONFLICT;
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

package com.huy.aiagentsystem.exception;

import com.huy.aiagentsystem.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Map<String, String>>>
    handleValidationException(MethodArgumentNotValidException ex) {

        System.out.println("NEW VALIDATION HANDLER");

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult()
                .getFieldErrors()
                .forEach(error ->
                        errors.put(
                                error.getField(),
                                error.getDefaultMessage()
                        )
                );

        ApiResponse<Map<String, String>> response =
                new ApiResponse<>(
                        false,
                        "Validation failed",
                        errors
                );

        return ResponseEntity
                .badRequest()
                .body(response);
    }

    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<ApiResponse<Object>>
    handleTaskNotFoundException(TaskNotFoundException ex) {

        ApiResponse<Object> response =
                new ApiResponse<>(
                        false,
                        ex.getMessage(),
                        null
                );

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(response);
    }

    @ExceptionHandler(AuthException.class)
    public ResponseEntity<ApiResponse<Object>>
    handleAuthException(AuthException ex) {

        ApiResponse<Object> response =
                new ApiResponse<>(
                        false,
                        ex.getMessage(),
                        null
                );

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(response);
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ApiResponse<Object>>
    handleEmailExists(EmailAlreadyExistsException ex) {

        ApiResponse<Object> response =
                new ApiResponse<>(
                        false,
                        ex.getMessage(),
                        null
                );

        return ResponseEntity
                .badRequest()
                .body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>>
    handleGeneralException(Exception ex) {

        ApiResponse<Object> response =
                new ApiResponse<>(
                        false,
                        "Internal server error",
                        null
                );

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(response);
    }
}
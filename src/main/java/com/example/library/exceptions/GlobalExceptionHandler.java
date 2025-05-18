package com.example.library.exceptions;

import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<?> handleNotFoundException(DataNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("error", ex.getMessage(), "status", HttpStatus.NOT_FOUND.value()));
    }
    @ExceptionHandler(InvalidValueException.class)
    public ResponseEntity<?> handleNotFoundException(InvalidValueException ex) {
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                .body(Map.of("error", ex.getMessage(), "status", HttpStatus.NOT_ACCEPTABLE.value()));
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGeneralException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", "Internal Server Error", "message", ex.getMessage()));
    }
}
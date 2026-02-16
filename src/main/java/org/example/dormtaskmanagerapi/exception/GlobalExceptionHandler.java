package org.example.dormtaskmanagerapi.exception;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ExMessage> handleException(EntityNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ExMessage(
                        "Entity Not Found",
                        e.getMessage(),
                        LocalDateTime.now()
                ));
    }

    @ExceptionHandler({
            MethodArgumentNotValidException.class
    })
    public ResponseEntity<ExMessage> handleException(Exception ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ExMessage(
                        "Bad Request",
                        ex.getMessage(),
                        LocalDateTime.now()
                ));
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<?> handleIllegalState(IllegalStateException ex) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(Map.of("error", ex.getMessage()));
    }

    @ExceptionHandler(EntityExistsException.class)
    public ResponseEntity<ExMessage> handleEntityExists(EntityExistsException ex) {
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(new ExMessage(
                        "Entity Already Exists",
                        ex.getMessage(),
                        LocalDateTime.now()
                ));
    }

}


package com.todo.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<StandartError> handleNotFoundException(NotFoundException ex) {
            return new ResponseEntity<>(
                    StandartError.builder()
                            .timestamp(LocalDateTime.now())
                            .message(ex.getMessage())
                            .cause(ex.getClass().getName())
                            .statusCode(HttpStatus.NOT_FOUND.value())
                            .build(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UnprocessableEntityException.class)
    public ResponseEntity<StandartError> handleNotUnprocessableException(UnprocessableEntityException ex) {
        return new ResponseEntity<>(
                StandartError.builder()
                        .timestamp(LocalDateTime.now())
                        .message(ex.getMessage())
                        .cause(ex.getClass().getName())
                        .statusCode(HttpStatus.UNPROCESSABLE_ENTITY.value())
                        .build(), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorDetails> handleValidationException(MethodArgumentNotValidException e) {
        return new ResponseEntity<>(
                ValidationErrorDetails.builder()
                        .timestamp(LocalDateTime.now())
                        .message("Validation Error")
                        .cause(e.getClass().getName())
                        .statusCode(HttpStatus.BAD_REQUEST.value())
                        .errors(buildErrors(e))
                        .build(), HttpStatus.BAD_REQUEST);
    }


    private Map<String, String> buildErrors(MethodArgumentNotValidException e) {
        Map<String, String> fieldErrors = new HashMap<>();
        e.getBindingResult().getAllErrors()
                .forEach((error) -> {
                    String fieldName = ((FieldError) error).getField();
                    String errorMessage = error.getDefaultMessage();
                    fieldErrors.put(fieldName, errorMessage);
                });
        return fieldErrors;
    }

}

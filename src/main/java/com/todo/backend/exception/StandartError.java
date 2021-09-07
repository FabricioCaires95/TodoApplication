package com.todo.backend.exception;

import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Data
@SuperBuilder
public class StandartError {

    private LocalDateTime timestamp;
    private String message;
    private Integer statusCode;
    private String cause;

}

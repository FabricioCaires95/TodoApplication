package com.todo.backend.exception;


import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.Map;

@SuperBuilder
@Getter
public class ValidationErrorDetails extends StandartError{
    private Map<String, String> errors;
}


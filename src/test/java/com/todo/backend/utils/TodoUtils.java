package com.todo.backend.utils;

import com.todo.backend.dto.TodoDto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class TodoUtils {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final LocalDate defaultDateTime = LocalDate.parse("01/09/2021", formatter);

    public static TodoDto getTodoDto() {
        return  TodoDto.builder()
                .id(1L)
                .title("Learning")
                .description("learning spring boot")
                .deadline(defaultDateTime)
                .status(false)
                .build();
    }

}

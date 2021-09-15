package com.todo.backend.utils;

import com.todo.backend.dto.TodoDto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class TodoUtils {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final LocalDate passDate = LocalDate.parse("10/09/2021", formatter);

    public static TodoDto getTodoDto() {
        return  TodoDto.builder()
                .id(1L)
                .title("Learning")
                .description("learning spring boot")
                .deadline(LocalDate.now())
                .status(false)
                .build();
    }

    public static TodoDto getTodoDtoWithDatePass() {
        return  TodoDto.builder()
                .id(1L)
                .title(null)
                .description("learning spring boot")
                .deadline(passDate)
                .status(false)
                .build();
    }

    public static TodoDto getNullId() {
        return  TodoDto.builder()
                .id(3L)
                .title("Learning")
                .description("learning spring boot")
                .deadline(passDate)
                .status(false)
                .build();
    }

}

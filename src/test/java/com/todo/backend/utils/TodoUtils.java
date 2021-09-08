package com.todo.backend.utils;

import com.todo.backend.dto.TodoDto;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TodoUtils {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    private static final LocalDateTime defaultDateTime = LocalDateTime.parse("01/09/2021 19:50", formatter);

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

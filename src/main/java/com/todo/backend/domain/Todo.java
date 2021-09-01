package com.todo.backend.domain;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class Todo {

    private Long id;
    private String title;
    private String description;
    private LocalDateTime deadline;
    private Boolean status;

}

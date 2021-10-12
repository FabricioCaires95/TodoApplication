package com.todo.backend.utils;

import com.todo.backend.domain.Todo;
import com.todo.backend.dto.TodoDto;
import com.todo.backend.dto.TodoUpdateDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

public class TodoUtils {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final LocalDate passDate = LocalDate.parse("10/09/2021", formatter);

    public static TodoDto getTodoDto() {
        return TodoDto.builder()
                .id(1L)
                .title("Learning")
                .description("learning spring boot")
                .deadline(LocalDate.now())
                .isFinished(false)
                .build();
    }

    public static Todo getTodoEntity() {
        return Todo.builder()
                .id(1L)
                .title("Learning")
                .description("learning spring boot")
                .deadline(LocalDate.now())
                .isFinished(false)
                .build();
    }

    public static TodoDto getTodoDtoToUpdate(){
        return TodoDto.builder()
                .id(2L)
                .title("Make something 2")
                .description("describe the task 2")
                .deadline(LocalDate.now().plusDays(3))
                .isFinished(true)
                .build();
    }

    public static TodoDto getTodoDtoWithDatePass() {
        return TodoDto.builder()
                .id(1L)
                .title(null)
                .description("learning spring boot")
                .deadline(passDate)
                .isFinished(false)
                .build();
    }

    public static TodoUpdateDto getUpdateTodoDtoWithComplet() {
        return TodoUpdateDto.builder()
                .id(1L)
                .title("Make something")
                .description("describe the task")
                .deadline(LocalDate.now().plusDays(2))
                .isFinished(true)
                .build();
    }

    public static TodoUpdateDto getUpdateTodoDtoWithInvalidArguments() {
        return TodoUpdateDto.builder()
                .id(1L)
                .title("")
                .description("")
                .deadline(passDate)
                .isFinished(false)
                .build();
    }

    public static TodoDto getTodoDtoWithInvalidArguments() {
        return TodoDto.builder()
                .id(null)
                .title("")
                .description("")
                .deadline(passDate)
                .isFinished(false)
                .build();
    }

    public static Page<TodoDto> returnDefaultPageable() {
        PageRequest request = PageRequest.of(0, 2, Sort.by("deadline").ascending());
        return new PageImpl<>(getFinishListTasks(), request, 2);
    }

    public static Page<Todo> returnEntityDefaultPageable() {
        PageRequest request = PageRequest.of(0, 2, Sort.by("deadline").ascending());
        return new PageImpl<>(getTodo(), request, 2);
    }

    public static List<TodoDto> getFinishListTasks() {
        TodoDto t1 = TodoDto.builder()
                .id(1L)
                .title("Make something")
                .description("describe the task")
                .deadline(LocalDate.now())
                .isFinished(false)
                .build();


        TodoDto t2 = TodoDto.builder()
                .id(2L)
                .title("Make something 2")
                .description("describe the task 2")
                .deadline(LocalDate.now())
                .isFinished(false)
                .build();
        return Arrays.asList(t1, t2);
    }


    public static List<Todo> getTodo() {
        Todo t1 = Todo.builder()
                .id(1L)
                .title("Make something")
                .description("describe the task")
                .deadline(LocalDate.now())
                .isFinished(false)
                .build();


        Todo t2 = Todo.builder()
                .id(2L)
                .title("Make something 2")
                .description("describe the task 2")
                .deadline(LocalDate.now())
                .isFinished(false)
                .build();
        return Arrays.asList(t1, t2);
    }

}

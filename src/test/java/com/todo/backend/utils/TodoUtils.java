package com.todo.backend.utils;

import com.todo.backend.domain.Todo;
import com.todo.backend.dto.TodoCreateDto;
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

    public static Todo getTodoEntityWithUser() {
        return Todo.builder()
                .id(3L)
                .title("Learning redis")
                .description("learning redis")
                .deadline(LocalDate.now().plusDays(3))
                .isFinished(false)
                .user(UserUtils.getUserEntity())
                .build();
    }

    public static TodoDto getTodoDtoToUpdate(){
        return TodoDto.builder()
                .id(3L)
                .title("redis")
                .description("learning redis")
                .deadline(LocalDate.now().plusDays(3))
                .isFinished(true)
                .build();
    }

    public static TodoDto getTodoForUser() {
        return TodoDto.builder()
                .id(1L)
                .title("teste 2 ")
                .description("learning spring boot")
                .deadline(LocalDate.now().plusDays(2))
                .isFinished(false)
                .user(UserUtils.getUserDto())
                .build();
    }

    public static TodoUpdateDto getUpdateTodoDtoWithComplet() {
        return TodoUpdateDto.builder()
                .id(3L)
                .title("Make something")
                .description("Updated teste")
                .deadline(LocalDate.now().plusDays(1))
                .isFinished(true)
                .userId(1L)
                .build();
    }

    public static TodoUpdateDto getUpdateTodoDtoForTest() {
        return TodoUpdateDto.builder()
                .id(3L)
                .title("redis")
                .description("learning redis")
                .deadline(LocalDate.now().plusDays(3))
                .isFinished(false)
                .userId(1L)
                .build();
    }

    public static TodoUpdateDto getUpdateTodoDtoWithInvalidArguments() {
        return TodoUpdateDto.builder()
                .id(null)
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

    public static TodoCreateDto getTodoCreateDto() {
        return TodoCreateDto.builder()
                .id(1L)
                .title("Learning")
                .description("learning spring boot")
                .deadline(LocalDate.now())
                .isFinished(false)
                .idUser(2L)
                .build();
    }

    public static TodoCreateDto getTodoCreateDtoInvalidInputs() {
        return TodoCreateDto.builder()
                .title("")
                .description("")
                .deadline(LocalDate.now().minusDays(1))
                .isFinished(false)
                .idUser(null)
                .build();
    }

    public static List<TodoDto> getListTodoDtoByUserId() {
        return Arrays.asList(getTodoForUser(), getTodoForUser());
    }


    public static List<Todo> getListTodoEntityByUserId() {
        return Arrays.asList(getTodoEntity(), getTodoEntity());
    }

}

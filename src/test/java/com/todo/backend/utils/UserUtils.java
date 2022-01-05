package com.todo.backend.utils;

import com.todo.backend.domain.Todo;
import com.todo.backend.domain.User;
import com.todo.backend.dto.TodoDto;
import com.todo.backend.dto.UserDto;
import com.todo.backend.dto.UserUpdateDto;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class UserUtils {


    public static User getUserEntity() {
       return new User(null, "spacer",
               "spacer@gmail.com", "sdadasdsada",
               Collections.emptySet());
    }


    public static UserDto getUserDto() {
        return UserDto.builder()
                .name("legend")
                .email("legend123@gmail.com")
                .password("asdadadafasfas")
                .build();
    }

    public static UserUpdateDto getUserUpdateDto() {
        return UserUpdateDto.builder()
                .id(1L)
                .name("legend")
                .email("legend155@gmail.com")
                .password("faf4587")
                .build();
    }

    public static UserUpdateDto getUserUpdateDtoWithInvalidFields() {
        return UserUpdateDto.builder()
                .name("legend")
                .email("legend15gmail.com")
                .password("faf")
                .build();
    }

    public static UserDto getUserDtoWithInvalidFields() {
        return UserDto.builder()
                .name("")
                .email("legend123gmail.com")
                .password("asd45")
                .build();
    }


    public static UserDto getDtoWithTasks() {
        UserDto user = new UserDto();
        user.setEmail("fabricio@gmail.com");
        user.setName("fabricio");
        user.setPassword("12345");
        user.setId(1L);

        TodoDto t1 = new TodoDto();
        t1.setId(2L);
        t1.setUser(user);
        t1.setDescription("teste1");
        t1.setIsFinished(false);
        t1.setTitle("teste2");
        t1.setDeadline(LocalDate.now());

        TodoDto t2 = new TodoDto();
        t2.setId(2L);
        t2.setUser(user);
        t2.setDescription("teste1");
        t2.setIsFinished(false);
        t2.setTitle("teste2");
        t2.setDeadline(LocalDate.now());

        Set<TodoDto> tasks = new HashSet<>();
        tasks.add(t1);
        tasks.add(t2);

        user.setTasks(tasks);

        return user;
    }

    public static  User getUserWithTasks() {
        User user = new User();
        user.setEmail("fabricio@gmail.com");
        user.setName("fabricio");
        user.setPassword("12345");
        user.setId(1L);

        Todo t1 = new Todo();
        t1.setId(2L);
        t1.setUser(user);
        t1.setDescription("teste1");
        t1.setIsFinished(false);
        t1.setTitle("teste2");
        t1.setDeadline(LocalDate.now());

        Todo t2 = new Todo();
        t2.setId(2L);
        t2.setUser(user);
        t2.setDescription("teste1");
        t2.setIsFinished(false);
        t2.setTitle("teste2");
        t2.setDeadline(LocalDate.now());

        Set<Todo> tasks = new HashSet<>();
        tasks.add(t1);
        tasks.add(t2);

        user.setTasks(tasks);

        return user;
    }
}

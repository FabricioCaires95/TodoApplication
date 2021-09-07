package com.todo.backend.controller;

import com.todo.backend.dto.TodoDto;
import com.todo.backend.service.TodoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static com.todo.backend.utils.TodoUtils.getTodoDto;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class TodoControllerTest {

    @InjectMocks
    private TodoController todoController;

    @Mock
    private TodoService todoService;

    @Test
    @DisplayName("Get a valid Todo by id")
    public void returnTodoDtoByIdSuccessful() {
        when(todoService.findById(anyLong())).thenReturn(getTodoDto());

        ResponseEntity<TodoDto> result = todoController.findById(anyLong());

        assertNotNull(result);
        assertNotNull(result.getBody());
        assertThat(result.getBody().getTitle()).isEqualTo(getTodoDto().getTitle());
        assertThat(result.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
    }

}

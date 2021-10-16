package com.todo.backend.service;

import com.todo.backend.domain.Todo;
import com.todo.backend.dto.TodoDto;
import com.todo.backend.dto.TodoUpdateDto;
import com.todo.backend.exception.NotFoundException;
import com.todo.backend.mapper.TodoMapper;
import com.todo.backend.repository.TodoRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Optional;

import static com.todo.backend.utils.TodoUtils.getTodoDto;
import static com.todo.backend.utils.TodoUtils.getTodoEntity;
import static com.todo.backend.utils.TodoUtils.getUpdateTodoDtoWithComplet;
import static com.todo.backend.utils.TodoUtils.returnEntityDefaultPageable;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.anyBoolean;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TodoServiceImplTest {

    @InjectMocks
    private TodoServiceImpl service;

    @Mock
    private TodoRepository repository;

    @Mock
    private TodoMapper todoMapper;

    @BeforeMethod
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getTodoByIdSuccess() {
        TodoDto t1 = getTodoDto();
        when(repository.findById(anyLong()))
                .thenReturn(Optional.of(getTodoEntity()));
        when(todoMapper.convertToDto(any())).thenReturn(t1);

        t1 = service.findById(anyLong());
        assertNotNull(t1);
        assertEquals(t1.getId(), t1.getId());
        assertEquals(t1.getTitle(), t1.getTitle());
        assertEquals(t1.getDescription(), t1.getDescription());
        assertEquals(t1.getDeadline(), t1.getDeadline());
        assertEquals(t1.getIsFinished(), t1.getIsFinished());

        verify(repository, times(1)).findById(anyLong());
    }

    @Test(expectedExceptions = NotFoundException.class)
    public void getNotFoundExceptionWhenFindById() {
        when(repository.findById(1L))
                .thenReturn(Optional.of(getTodoEntity()));
        service.findById(2L);
    }

    @Test
    public void createNewTaskSuccessful() {
        when(repository.save(any())).thenReturn(getTodoEntity());
        service.createTodo(getTodoDto());
    }

    @Test
    public void deleteTaskSuccessful() {
        when(repository.save(any())).thenReturn(getTodoEntity());
        service.deleteById(anyLong());
    }

    @Test
    public void updateTaskSuccessful() {
        TodoUpdateDto updateDto = getUpdateTodoDtoWithComplet();
        Todo t1 = getTodoEntity();
        TodoDto dto = new TodoDto();
        when(repository.findById(t1.getId())).thenReturn(Optional.of(t1));
        when(todoMapper.convertUpdateDtoToEntity(any())).thenReturn(t1);
        when(todoMapper.convertToDto(any())).thenReturn(dto);
        dto = service.update(updateDto);

        assertNotNull(dto);
        assertNotEquals(updateDto.getTitle(), t1.getTitle());
        assertNotEquals(updateDto.getDescription(), t1.getDescription());
        assertNotEquals(updateDto.getDeadline(), t1.getDeadline());
        assertNotEquals(updateDto.getIsFinished(), t1.getIsFinished());
    }

    @Test(expectedExceptions = NotFoundException.class)
    public void notFoundUpdateTask() {
        TodoUpdateDto updateDto = getUpdateTodoDtoWithComplet();
        when(repository.findById(anyLong())).thenReturn(Optional.empty());
        service.update(updateDto);
    }

    @Test
    public void getAllTasksSuccessful() {
        Page<Todo> todoPage1 = returnEntityDefaultPageable();
        when(repository.findAllByIsFinished(any(), anyBoolean())).thenReturn(todoPage1);

        Page<TodoDto> dtoPage = service.findAllDynamicParameters(0, 2, false);

        assertNotNull(dtoPage);
        assertEquals(dtoPage.getContent().size(), 2);

    }


}

package com.todo.backend.service;

import com.todo.backend.domain.Todo;
import com.todo.backend.dto.TodoDto;
import com.todo.backend.exception.NotFoundException;
import com.todo.backend.exception.UnprocessableEntityException;
import com.todo.backend.mapper.TodoMapper;
import com.todo.backend.repository.TodoRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Optional;

import static com.todo.backend.utils.TodoUtils.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

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
    public void shouldUpdateTaskSuccessful() {
        TodoDto todoDto = getTodoDtoToUpdate();

        Todo t1 = getTodoEntity();
        when(repository.findById(anyLong())).thenReturn(Optional.of(t1));
        todoDto = service.update(todoDto);

        assertNotNull(todoDto);
        assertNotEquals(todoDto.getTitle(), t1.getTitle());
        assertNotEquals(todoDto.getDescription(), t1.getDescription());
        assertNotEquals(todoDto.getDeadline(), t1.getDeadline());
        assertNotEquals(todoDto.getIsFinished(), t1.getIsFinished());
    }

    @Test(expectedExceptions = UnprocessableEntityException.class)
    public void returnErrorWhenPassingNullId() {
        TodoDto todoDto = TodoDto.builder()
                .id(null)
                .build();
        service.update(todoDto);
    }

    @Test(expectedExceptions = NotFoundException.class)
    public void notFoundUpdateTask() {
        TodoDto updateDto = getTodoDto();
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

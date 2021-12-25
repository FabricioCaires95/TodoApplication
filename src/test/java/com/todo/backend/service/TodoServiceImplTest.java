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

import java.util.List;
import java.util.Optional;

import static com.todo.backend.utils.TodoUtils.getFinishListTasks;
import static com.todo.backend.utils.TodoUtils.getTodo;
import static com.todo.backend.utils.TodoUtils.getTodoCreateDto;
import static com.todo.backend.utils.TodoUtils.getTodoDto;
import static com.todo.backend.utils.TodoUtils.getTodoDtoToUpdate;
import static com.todo.backend.utils.TodoUtils.getTodoEntity;
import static com.todo.backend.utils.TodoUtils.getTodoEntityWithUser;
import static com.todo.backend.utils.TodoUtils.getUpdateTodoDtoForTest;
import static com.todo.backend.utils.TodoUtils.returnEntityDefaultPageable;
import static org.junit.jupiter.api.Assertions.assertEquals;
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
        verify(repository, times(1)).findById(anyLong());
    }

    @Test
    public void createNewTaskSuccessful() {
        when(repository.save(any())).thenReturn(getTodoEntity());
        service.createTodo(getTodoDto());
        verify(repository, times(1)).save(any());
    }

    @Test
    public void deleteTaskSuccessful() {
        when(repository.save(any())).thenReturn(getTodoEntity());
        service.deleteById(anyLong());
        verify(repository, times(1)).deleteById(anyLong());
    }

    @Test
    public void updateTaskSuccessful() {
        TodoUpdateDto updatedEntity = getUpdateTodoDtoForTest();
        Todo newEntityConverted = getTodoEntityWithUser();
        TodoDto result = getTodoDtoToUpdate();

        when(todoMapper.convertUpdateDtoToEntity(any())).thenReturn(newEntityConverted);
        when(repository.save(any())).thenReturn(newEntityConverted);
        when(todoMapper.convertToDto(any())).thenReturn(result);

        result = service.update(updatedEntity);

        assertNotNull(result);
        assertEquals(updatedEntity.getTitle(), result.getTitle());
        assertEquals(updatedEntity.getId(), result.getId());
        assertEquals(updatedEntity.getDescription(), result.getDescription());
        assertEquals(updatedEntity.getDeadline(), result.getDeadline());
        verify(todoMapper).convertUpdateDtoToEntity(any());
        verify(todoMapper).convertToDto(any());
        verify(repository).save(any());
    }

    @Test
    public void getAllTasksSuccessful() {
        Page<Todo> todoPage1 = returnEntityDefaultPageable();
        when(repository.findAllByIsFinishedAndUserId(any(), anyBoolean(), anyLong())).thenReturn(todoPage1);

        Page<TodoDto> dtoPage = service.findAllByDynamicParameters(0, 2, false, 1L);

        assertNotNull(dtoPage);
        assertEquals(dtoPage.getContent().size(), 2);
        verify(repository, times(1)).findAllByIsFinishedAndUserId(any(), anyBoolean(), anyLong());

    }

    @Test
    public void getAllTasksWithoutPageable() {
        List<Todo> todoList = getTodo();
        List<TodoDto> todoDtos = getFinishListTasks();

        when(repository.findAll()).thenReturn(todoList);
        when(todoMapper.convertList(todoList)).thenReturn(todoDtos);

        todoDtos = service.findAll();

        assertEquals(todoDtos.size(), 2);
        assertEquals(todoDtos.get(0).getDescription(), "describe the task");
        assertEquals(todoDtos.get(0).getId(), 1L);
        assertEquals(todoDtos.get(1).getId(), 2L);
        assertEquals(todoDtos.get(1).getDescription(), "describe the task 2");
        verify(repository, times(1)).findAll();
    }

    @Test
    public void createNewTaskSuccessfulWithMethod() {
        when(todoMapper.convertCreateDtoToEntity(any())).thenReturn(getTodoEntity());

        service.createTodoForUser(getTodoCreateDto());

        verify(repository, times(1)).save(any());
    }
}

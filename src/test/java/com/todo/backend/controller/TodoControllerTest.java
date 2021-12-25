package com.todo.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.todo.backend.exception.NotFoundException;
import com.todo.backend.exception.RestExceptionHandler;
import com.todo.backend.service.TodoService;
import org.junit.jupiter.api.DisplayName;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.todo.backend.utils.TodoUtils.getTodoCreateDto;
import static com.todo.backend.utils.TodoUtils.getTodoCreateDtoInvalidInputs;
import static com.todo.backend.utils.TodoUtils.getTodoDto;
import static com.todo.backend.utils.TodoUtils.getUpdateTodoDtoWithComplet;
import static com.todo.backend.utils.TodoUtils.getUpdateTodoDtoWithInvalidArguments;
import static com.todo.backend.utils.TodoUtils.returnDefaultPageable;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.anyBoolean;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebAppConfiguration
@ContextConfiguration(classes = {MockServletContext.class})
public class TodoControllerTest extends AbstractTestNGSpringContextTests {

  @InjectMocks
  private TodoController controller;

  @Mock
  private TodoService todoService;

  private ObjectMapper mapper;

  protected MockMvc mvc;

  @BeforeMethod
  public void beforeMethod() {
    MockitoAnnotations.openMocks(this);
    mvc = MockMvcBuilders.standaloneSetup(controller).setControllerAdvice(new RestExceptionHandler()).build();
    this.mapper = new ObjectMapper()
            .registerModule(new Jdk8Module())
            .registerModule(new JavaTimeModule());
  }

  @Test
  @DisplayName("get TodoDto by id sucessful")
  public void shouldReturnTodoByIdSuccessful() throws Exception {
    when(todoService.findById(anyLong())).thenReturn(getTodoDto());
    mvc.perform(get(
            "/todo/{id}", 1L
    )).andExpect(status().isOk());
  }

  @Test
  @DisplayName("Error 404 when todo has no found by id")
  public void returnErrorWhenTodoNoFoundById() throws Exception {
    when(todoService.findById(anyLong())).thenThrow(NotFoundException.class);
    mvc.perform(get(
            "/todo/{id}", 1L
    )).andExpect(status().isNotFound());
  }

  @Test
  @DisplayName("create new TodoDto successful")
  public void createNewTodoSuccessful() throws Exception {
    mvc.perform(post(
                    "/todo/create")
                    .contentType(APPLICATION_JSON)
                    .content(mapper.writeValueAsString(getTodoCreateDto())))
                    .andExpect(status().isCreated());
  }

  @Test
  @DisplayName("test passing invalid request arguments ")
  public void shouldReturnBadRequestGivenInvalidArguments() throws Exception {
    mvc.perform(post(
                    "/todo/create")
                    .contentType(APPLICATION_JSON)
                    .content(mapper.writeValueAsString(getTodoCreateDtoInvalidInputs())))
            .andExpect(status().isBadRequest())
            .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException))
            .andExpect(jsonPath("$.message").value("Validation Error"))
            .andExpect(jsonPath("$.errors.['deadline']").value("must be a date in the present or in the future"))
            .andExpect(jsonPath("$.errors.['title']").value("title is required"))
            .andExpect(jsonPath("$.errors.['description']").value("description is required"))
            .andExpect(jsonPath("$.errors.['idUser']").value("User id is required"));
  }

  @Test
  @DisplayName("test update a task successfuly")
  public void shouldUpdateSuccessful() throws Exception {
    mvc.perform(put(
                    "/todo/update")
                    .contentType(APPLICATION_JSON)
                    .content(mapper.writeValueAsString(getUpdateTodoDtoWithComplet())))
            .andExpect(status().isOk());
  }

  @Test
  @DisplayName("pass invalid arguments to update endpoint")
  public void shouldReturn401WithCustomErrors() throws Exception {
    mvc.perform(put(
                    "/todo/update")
                    .contentType(APPLICATION_JSON)
                    .content(mapper.writeValueAsString(getUpdateTodoDtoWithInvalidArguments())))
            .andExpect(jsonPath("$.errors['id']").value("ID is required"))
            .andExpect(jsonPath("$.errors['title']").value("title is required"))
            .andExpect(jsonPath("$.errors['description']").value("description is required"))
            .andExpect(jsonPath("$.errors['deadline']").value("must be a date in the present or in the future"))
            .andExpect(jsonPath("$.errors['userId']").value("User id is Required"))
            .andExpect(status().isBadRequest());
    verify(todoService, times(0)).update(any());
  }

  @Test
  @DisplayName("delete a task successful")
  public void shouldDeleteSuccessful() throws Exception {
    mvc.perform(delete("/todo/{id}", 1L)
                    .contentType(APPLICATION_JSON))
            .andExpect(status().isNoContent());
    verify(todoService, times(1)).deleteById(1L);
  }

  @Test
  @DisplayName("find tasks finished by default parameters")
  public void getTaskFinishedWithDefaultParametersSuccessful() throws Exception {
    when(todoService.findAllByDynamicParameters(anyInt(), anyInt(), anyBoolean())).thenReturn(returnDefaultPageable());
    mvc.perform(get(
                    "/todo/all"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.size").value(2))
            .andExpect(jsonPath("$.content[0].id").value(1L))
            .andExpect(jsonPath("$.content[0].isFinished").value(false))
            .andExpect(jsonPath("$.content[1].id").value(2L))
            .andExpect(jsonPath("$.content[1].isFinished").value(false));

    verify(todoService, times(1)).findAllByDynamicParameters(anyInt(), anyInt(), anyBoolean());
  }

  @Test
  @DisplayName("Passing specified parameters on request")
  public void shouldPassSpecifiedArguments() throws Exception {
    mvc.perform(get(
                    "/todo/all?page=2&size=5&isFinished=true"))
            .andExpect(status().isOk());
    verify(todoService, times(1)).findAllByDynamicParameters(2, 5, true);
  }


}

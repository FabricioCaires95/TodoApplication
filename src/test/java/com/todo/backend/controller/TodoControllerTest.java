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
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.todo.backend.utils.TodoUtils.getTodoDto;
import static com.todo.backend.utils.TodoUtils.getTodoDtoWithDatePass;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
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
    mvc.perform(MockMvcRequestBuilders.get(
        "/todo/{id}", 1L
    )).andExpect(status().isOk());
  }

  @Test
  @DisplayName("Error 404 when todo has no found by id")
  public void returnErrorWhenTodoNoFoundById() throws Exception {
    when(todoService.findById(anyLong())).thenThrow(NotFoundException.class);
    mvc.perform(MockMvcRequestBuilders.get(
            "/todo/{id}", 1L
    )).andExpect(status().isNotFound());
  }

  @Test
  @DisplayName("create new TodoDto successful")
  public void createNewTodoSuccessful() throws Exception {
    mvc.perform(MockMvcRequestBuilders.post(
            "/todo/create")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(mapper.writeValueAsString(getTodoDto())))
                    .andExpect(status().isCreated());
  }

  @Test
  @DisplayName("test passing invalid request arguments ")
  public void shouldReturnBadRequestGivenInvalidArguments() throws Exception {
    mvc.perform(MockMvcRequestBuilders.post(
                            "/todo/create")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(mapper.writeValueAsString(getTodoDtoWithDatePass())))
                    .andExpect(status().isBadRequest())
                    .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException))
            .andExpect(jsonPath("$.message").value("Validation Error"))
            .andExpect(jsonPath("$.errors.['deadline']").value("Invalide date"))
            .andExpect(jsonPath("$.errors.['title']").value("name is required"));
  }

}

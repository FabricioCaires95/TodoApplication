package com.todo.backend.controller;

import com.todo.backend.service.TodoService;
import org.junit.jupiter.api.DisplayName;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.todo.backend.utils.TodoUtils.getTodoDto;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@WebAppConfiguration
@ContextConfiguration(classes = {MockServletContext.class})
public class TodoControllerTest extends AbstractTestNGSpringContextTests {

  @InjectMocks
  private TodoController controller;

  @Mock
  private TodoService todoService;

  @Autowired
  private MockServletContext servletContext;

  protected MockMvc mvc;

  protected HttpHeaders httpHeaders;

  @BeforeMethod
  public void beforeMethod() {
    MockitoAnnotations.openMocks(this);
    mvc = MockMvcBuilders.standaloneSetup(controller).build();
  }

  @Test
  @DisplayName("get TodoDto by id sucessful")
  public void testFindByIdSuccesful() throws Exception {
    when(todoService.findById(anyLong())).thenReturn(getTodoDto());
    mvc.perform(MockMvcRequestBuilders.get(
        "/todo/{id}", 1L
    )).andExpect(MockMvcResultMatchers.status().isOk());
  }


}

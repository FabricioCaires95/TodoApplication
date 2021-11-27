package com.todo.backend.controller;

import com.todo.backend.exception.NotFoundException;
import com.todo.backend.exception.RestExceptionHandler;
import com.todo.backend.service.UserService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.todo.backend.utils.UserUtils.getUserDto;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebAppConfiguration
@ContextConfiguration(classes = {MockServletContext.class})
public class UserControllerTest extends AbstractTestNGSpringContextTests {

    @InjectMocks
    private UserController controller;

    @Mock
    private UserService userService;

    protected MockMvc mvc;

    @BeforeMethod
    public void beforeMethod() {
        MockitoAnnotations.openMocks(this);
        mvc = MockMvcBuilders.standaloneSetup(controller).setControllerAdvice(new RestExceptionHandler()).build();
    }

    @Test
    public void shouldReturnUserById() throws Exception {
        when(userService.findUserById(anyLong())).thenReturn(getUserDto());
        mvc.perform(get("/user/{id}", anyLong())
                .contentType(APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldReturn404AsUserNotFound() throws Exception {
        when(userService.findUserById(anyLong())).thenThrow(NotFoundException.class);
        mvc.perform(get("/user/{id}", anyLong())
                        .contentType(APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldReturnUserByEmail() throws Exception {
        when(userService.findUserByEmail(anyString())).thenReturn(getUserDto());
        mvc.perform(get("/user?email=space123@gmail.com")
                        .contentType(APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
    }

    @Test
    public void testWhenEmailIsNotFound() throws Exception {
        when(userService.findUserByEmail(anyString())).thenThrow(NotFoundException.class);
        mvc.perform(get("/user?email=space123@gmail.com")
                        .contentType(APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound());
    }
}

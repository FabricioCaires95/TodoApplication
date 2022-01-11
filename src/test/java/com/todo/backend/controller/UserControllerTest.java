package com.todo.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
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
import static com.todo.backend.utils.UserUtils.getUserDtoWithInvalidFields;
import static com.todo.backend.utils.UserUtils.getUserUpdateDto;
import static com.todo.backend.utils.UserUtils.getUserUpdateDtoWithInvalidFields;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebAppConfiguration
@ContextConfiguration(classes = {MockServletContext.class})
public class UserControllerTest extends AbstractTestNGSpringContextTests {

    @InjectMocks
    private UserController controller;

    @Mock
    private UserService userService;

    protected MockMvc mvc;

    private ObjectMapper mapper;

    @BeforeMethod
    public void beforeMethod() {
        MockitoAnnotations.openMocks(this);
        mvc = MockMvcBuilders.standaloneSetup(controller).setControllerAdvice(new RestExceptionHandler()).build();
        this.mapper = new ObjectMapper()
                .registerModule(new Jdk8Module())
                .registerModule(new JavaTimeModule());
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

    @Test
    public void shouldGetBadRequestWhenReceiveInvalidFieldsOnRequest() throws Exception {
        mvc.perform(post("/user/create")
                .contentType(APPLICATION_JSON_VALUE)
                .content(mapper.writeValueAsString(getUserDtoWithInvalidFields())))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Validation Error"))
                .andExpect(jsonPath("$.errors['name']").value("Name is required"))
                .andExpect(jsonPath("$.errors['email']").value("Email is invalid"))
                .andExpect(jsonPath("$.errors['password']").value("Password is too short"));
        verify(userService, never()).createUser(any());
    }

    @Test
    public void testUpdateUserSuccessful() throws Exception {
        mvc.perform(put("/user/update")
                        .contentType(APPLICATION_JSON_VALUE)
                        .content(mapper.writeValueAsString(getUserUpdateDto())))
                .andExpect(status().isNoContent());
        verify(userService, times(1)).update(any());
    }

    @Test
    public void testGetBadRequestWhenPassingInvalidFields() throws Exception {
        mvc.perform(put("/user/update")
                        .contentType(APPLICATION_JSON_VALUE)
                        .content(mapper.writeValueAsString(getUserUpdateDtoWithInvalidFields())))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Validation Error"))
                .andExpect(jsonPath("$.errors['id']").value("User id is required"));
    }
}

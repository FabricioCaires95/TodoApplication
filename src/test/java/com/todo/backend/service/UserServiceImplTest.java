package com.todo.backend.service;

import com.todo.backend.dto.UserDto;
import com.todo.backend.exception.AuthorizationException;
import com.todo.backend.mapper.CycleAvoidingMappingContext;
import com.todo.backend.mapper.UserMapper;
import com.todo.backend.repository.UserRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Optional;

import static com.todo.backend.utils.UserUtils.getUserDto;
import static com.todo.backend.utils.UserUtils.getUserEntity;
import static com.todo.backend.utils.UserUtils.getUserUpdateDto;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


public class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private CycleAvoidingMappingContext avoidingMappingContext;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @Mock
    private AuthorizationService authorizationService;

    @BeforeMethod
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void createUserSucessful() {
        var user = getUserEntity();
        when(userMapper.mapToEntity(getUserDto(), avoidingMappingContext)).thenReturn(user);
        when(passwordEncoder.encode(anyString())).thenReturn(getUserEntity().getPassword());
        when(userRepository.save(user)).thenReturn(user);

        userService.createUser(getUserDto());

        verify(userRepository, times(1)).save(any());
    }

    @Test
    public void getUserByIdSuccess() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(getUserEntity()));
        when(userMapper.mapToDto(any(), any())).thenReturn(getUserDto());

        UserDto userDto = userService.findUserById(anyLong());

        assertNotNull(userDto);
        assertEquals(userDto.getEmail(), "legend123@gmail.com");
        assertEquals(userDto.getName(), "legend");
        verify(userRepository, times(1)).findById(anyLong());
        verify(authorizationService, times(1)).isAuthorizated(anyLong());
    }

    @Test(expectedExceptions = AuthorizationException.class)
    public void shouldGetAuthorizationError() {
        doThrow(AuthorizationException.class)
                .when(authorizationService).isAuthorizated(anyLong());

        UserDto userDto = userService.findUserById(anyLong());

        assertNull(userDto);
        verify(authorizationService, times(1)).getUserAuthenticated();
    }

    @Test
    public void getUserByEmailSuccess() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(getUserEntity()));
        when(userMapper.mapToDto(any(), any())).thenReturn(getUserDto());

        UserDto userDto = userService.findUserByEmail(anyString());

        assertNotNull(userDto);
        assertEquals(userDto.getEmail(), "legend123@gmail.com");
        assertEquals(userDto.getName(), "legend");
        verify(userRepository, times(1)).findByEmail(anyString());
    }

    @Test
    public void updateUserSuccessful() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(getUserEntity()));
        when(passwordEncoder.encode(anyString())).thenReturn(getUserEntity().getPassword());

        userService.update(getUserUpdateDto());

        verify(userRepository).save(any());
    }

}

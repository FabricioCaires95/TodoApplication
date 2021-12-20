package com.todo.backend.service;

import com.todo.backend.dto.UserDto;
import com.todo.backend.mapper.CycleAvoidingMappingContext;
import com.todo.backend.mapper.UserMapper;
import com.todo.backend.repository.UserRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Optional;

import static com.todo.backend.utils.UserUtils.getUserDto;
import static com.todo.backend.utils.UserUtils.getUserEntity;
import static com.todo.backend.utils.UserUtils.getUserUpdateDto;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
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

    @BeforeMethod
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void createUserSucessful() {
        when(userRepository.save(any())).thenReturn(getUserEntity());
        when(userMapper.mapToEntity(getUserDto(), avoidingMappingContext)).thenReturn(getUserEntity());
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
        assertEquals(userDto.getPassword(), "asdadadafasfas");
        verify(userRepository, times(1)).findById(anyLong());
    }

    @Test
    public void getUserByEmailSuccess() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(getUserEntity()));
        when(userMapper.mapToDto(any(), any())).thenReturn(getUserDto());
        UserDto userDto = userService.findUserByEmail(anyString());
        assertNotNull(userDto);
        assertEquals(userDto.getEmail(), "legend123@gmail.com");
        verify(userRepository, times(1)).findByEmail(anyString());
    }

    @Test
    public void updateUserSuccessful() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(getUserEntity()));

        userService.update(getUserUpdateDto());

        verify(userRepository).save(any());
    }

}

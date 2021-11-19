package com.todo.backend.service;

import com.todo.backend.mapper.UserMapper;
import com.todo.backend.repository.UserRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.todo.backend.utils.UserUtils.getUserDto;
import static com.todo.backend.utils.UserUtils.getUserEntity;
import static org.mockito.ArgumentMatchers.any;
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

    @BeforeMethod
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void createUserSucessful() {
        when(userMapper.dtoToEntity(any())).thenReturn(getUserEntity());
        when(userRepository.save(any())).thenReturn(getUserEntity());
        userService.createUser(getUserDto());
        verify(userRepository, times(1)).save(any());

    }


}

package com.todo.backend.service;

import com.todo.backend.repository.UserRepository;
import com.todo.backend.security.UserSecurityDto;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Optional;

import static com.todo.backend.utils.UserUtils.getUserEntityAuth;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


public class UserDetailServiceImplTest {

    @InjectMocks
    private UserDetailServiceImpl userDetailsService;

    @Mock
    private UserRepository userRepository;

    @BeforeMethod
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSuccessOnGetValidUser() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.ofNullable(getUserEntityAuth()));

        UserSecurityDto securityDto = (UserSecurityDto) userDetailsService.loadUserByUsername(anyString());

        assertNotNull(securityDto);
        assertEquals(securityDto.getId(), getUserEntityAuth().getId());
        assertEquals(securityDto.getUsername(), getUserEntityAuth().getEmail());
        assertEquals(securityDto.getPassword(), getUserEntityAuth().getPassword());
        assertEquals(securityDto.getAuthorities().size(), 1);
        verify(userRepository, times(1)).findByEmail(anyString());
    }

    @Test(expectedExceptions = UsernameNotFoundException.class)
    public void testExceptionWhenUserNotFound() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        userDetailsService.loadUserByUsername(anyString());
    }


}

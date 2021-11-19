package com.todo.backend.service;

import com.todo.backend.dto.UserDto;
import com.todo.backend.mapper.UserMapper;
import com.todo.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    public void createUser(UserDto userDto) {
        userRepository.save(userMapper.dtoToEntity(userDto));
    }
}

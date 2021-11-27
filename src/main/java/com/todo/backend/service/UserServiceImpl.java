package com.todo.backend.service;

import com.todo.backend.dto.UserDto;
import com.todo.backend.exception.NotFoundException;
import com.todo.backend.mapper.UserMapper;
import com.todo.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private static final String DEFAULT_NOT_FOUND_MSG = "User was not found !";

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Override
    public void createUser(UserDto userDto) {
        userRepository.save(userMapper.dtoToEntity(userDto));
    }

    @Override
    public UserDto findUserById(Long id) {
        return userRepository.findById(id)
                .map(userEntity -> userMapper.entityToDto(userEntity))
                .orElseThrow(() -> new NotFoundException(DEFAULT_NOT_FOUND_MSG));
    }

    @Override
    public UserDto findUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(user -> userMapper.entityToDto(user))
                .orElseThrow(() -> new NotFoundException(DEFAULT_NOT_FOUND_MSG));
    }
}
package com.todo.backend.service;

import com.todo.backend.domain.User;
import com.todo.backend.dto.UserDto;
import com.todo.backend.dto.UserUpdateDto;
import com.todo.backend.exception.NotFoundException;
import com.todo.backend.mapper.CycleAvoidingMappingContext;
import com.todo.backend.mapper.UserMapper;
import com.todo.backend.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private static final String DEFAULT_NOT_FOUND_MSG = "User was not found !";

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private CycleAvoidingMappingContext avoidingMappingContext;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public void createUser(UserDto userDto) {
        var user = userMapper.mapToEntity(userDto, avoidingMappingContext);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    public UserDto findUserById(Long id) {
        return userRepository.findById(id)
                .map(userEntity -> userMapper.mapToDto(userEntity, avoidingMappingContext))
                .orElseThrow(() -> new NotFoundException(DEFAULT_NOT_FOUND_MSG));
    }

    @Override
    public UserDto findUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(user -> userMapper.mapToDto(user, avoidingMappingContext))
                .orElseThrow(() -> new NotFoundException(DEFAULT_NOT_FOUND_MSG));
    }

    @Override
    public User findUserEntity(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException(DEFAULT_NOT_FOUND_MSG));
    }

    @Override
    public void update(UserUpdateDto userDto) {
        log.info("New Password: {}", userDto.getPassword());
        var user = userMapper.convertToEntity(userDto, avoidingMappingContext);
        log.info("Password to be encoded: {}", user.getPassword());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }
}

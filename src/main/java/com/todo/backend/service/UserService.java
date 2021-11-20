package com.todo.backend.service;

import com.todo.backend.dto.UserDto;

public interface UserService {

    void createUser(UserDto userDto);

    UserDto findUserById(Long id);

    UserDto findUserByEmail(String email);
}

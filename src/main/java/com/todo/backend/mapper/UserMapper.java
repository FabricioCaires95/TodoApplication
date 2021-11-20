package com.todo.backend.mapper;

import com.todo.backend.domain.User;
import com.todo.backend.dto.UserDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User dtoToEntity(UserDto userDto);

    UserDto entityToDto(User user);
}

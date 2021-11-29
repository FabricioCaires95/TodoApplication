package com.todo.backend.mapper;

import com.todo.backend.domain.User;
import com.todo.backend.dto.UserDto;
import org.mapstruct.Context;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User mapToEntity(UserDto userDto, @Context CycleAvoidingMappingContext cycleAvoidingMappingContext);

    UserDto mapToDto(User user, @Context CycleAvoidingMappingContext cycleAvoidingMappingContext);
}

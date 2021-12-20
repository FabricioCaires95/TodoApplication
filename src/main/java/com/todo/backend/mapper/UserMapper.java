package com.todo.backend.mapper;

import com.todo.backend.domain.User;
import com.todo.backend.dto.UserDto;
import com.todo.backend.dto.UserUpdateDto;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User mapToEntity(UserDto userDto, @Context CycleAvoidingMappingContext cycleAvoidingMappingContext);

    @Mapping(target = "tasks", ignore = true)
    User convertToEntity(UserUpdateDto userDto, @Context CycleAvoidingMappingContext cycleAvoidingMappingContext);

    UserDto mapToDto(User user, @Context CycleAvoidingMappingContext cycleAvoidingMappingContext);
}

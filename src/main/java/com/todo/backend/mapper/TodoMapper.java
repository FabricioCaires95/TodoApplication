package com.todo.backend.mapper;

import com.todo.backend.domain.Todo;
import com.todo.backend.dto.TodoDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TodoMapper {

  TodoDto convertToDto(Todo todo);

  Todo convertToEntity(TodoDto dto);

}

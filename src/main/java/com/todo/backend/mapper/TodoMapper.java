package com.todo.backend.mapper;

import com.todo.backend.domain.Todo;
import com.todo.backend.dto.TodoDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TodoMapper {

  TodoDto convertToDto(Todo todo);

  Todo convertToEntity(TodoDto dto);

  List<TodoDto> returnDtoList(List<Todo> todo);
}

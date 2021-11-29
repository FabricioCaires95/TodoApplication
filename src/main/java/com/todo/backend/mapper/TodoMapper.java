package com.todo.backend.mapper;

import com.todo.backend.domain.Todo;
import com.todo.backend.dto.TodoDto;
import com.todo.backend.dto.TodoUpdateDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TodoMapper {

  @Mapping(target = "user.tasks", ignore = true)
  TodoDto convertToDto(Todo todo);

  @Mapping(target = "user.tasks", ignore = true)
  Todo convertToEntity(TodoDto dto);

  Todo convertUpdateDtoToEntity(TodoUpdateDto todoUpdateDto);

  @Mapping(target = "user.tasks", ignore = true)
  List<TodoDto> convertList(List<Todo> todos);

}

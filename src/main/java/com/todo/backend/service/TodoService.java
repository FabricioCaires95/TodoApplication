package com.todo.backend.service;

import com.todo.backend.dto.TodoDto;
import com.todo.backend.dto.TodoUpdateDto;
import org.springframework.data.domain.Page;

public interface TodoService {

  TodoDto findById(Long id);

  void createTodo(TodoDto todoDto);

  void deleteById(Long id);

  TodoUpdateDto update(TodoUpdateDto todoDto);

  Page<TodoDto> findAllDynamicParameters(Integer page, Integer size, boolean status);
}

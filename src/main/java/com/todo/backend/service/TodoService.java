package com.todo.backend.service;

import com.todo.backend.dto.TodoDto;
import org.springframework.data.domain.Page;

public interface TodoService {

  TodoDto findById(Long id);

  void createTodo(TodoDto todoDto);

  void deleteById(Long id);

  TodoDto update(TodoDto todoDto);

  Page<TodoDto> findAllDynamicParameters(Integer page, Integer size, boolean status);
}

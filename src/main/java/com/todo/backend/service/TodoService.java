package com.todo.backend.service;

import com.todo.backend.dto.TodoDto;
import com.todo.backend.dto.TodoUpdateDto;

import java.util.List;

public interface TodoService {

  TodoDto findById(Long id);

  List<TodoDto> findOpenTodo();

  List<TodoDto> findClosedTodo();

  List<TodoDto> findAllTodos();

  void createTodo(TodoDto todoDto);

  void deleteById(Long id);

  TodoDto update(TodoUpdateDto todoDto);
}

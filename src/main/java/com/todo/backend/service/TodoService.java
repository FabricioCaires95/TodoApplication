package com.todo.backend.service;

import com.todo.backend.dto.TodoDto;

import java.util.List;

public interface TodoService {

  TodoDto findById(Long id);

  List<TodoDto> findOpenTodo();

  List<TodoDto> findClosedTodo();

  List<TodoDto> findAllTodos();

  void createTodo(TodoDto todoDto);
}

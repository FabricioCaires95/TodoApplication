package com.todo.backend.service;

import com.todo.backend.dto.TodoDto;
import com.todo.backend.dto.TodoUpdateDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface TodoService {

  TodoDto findById(Long id);

  void createTodo(TodoDto todoDto);

  void deleteById(Long id);

  TodoDto update(TodoUpdateDto todoDto);

  Page<TodoDto> findAllByDynamicParameters(Integer page, Integer size, boolean status);

  List<TodoDto> findAll();

  List<TodoDto> getTodosByUserId(Long userId);
}

package com.todo.backend.service;

import com.todo.backend.dto.TodoDto;

public interface TodoService {

  TodoDto findById(Long id);
}

package com.todo.backend.service;

import com.todo.backend.domain.Todo;
import com.todo.backend.dto.TodoDto;
import com.todo.backend.mapper.TodoMapper;
import com.todo.backend.repository.TodoRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TodoServiceImpl implements TodoService {

  @Autowired
  private TodoRepository todoRepository;

  @Autowired
  private TodoMapper mapper;

  @Override
  public TodoDto findById(Long id) {
    Optional<Todo> todo = todoRepository.findById(id);
    return todo
        .map(value -> mapper.convertToDto(value))
        .orElse(null);
  }
}

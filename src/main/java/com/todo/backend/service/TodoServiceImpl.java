package com.todo.backend.service;

import com.todo.backend.domain.Todo;
import com.todo.backend.dto.TodoDto;
import com.todo.backend.mapper.TodoMapper;
import com.todo.backend.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

  @Override
  public List<TodoDto> findOpenTodo() {
    return mapper.returnDtoList(todoRepository.findAllByStatusFalseOrderByDeadline());
  }

  @Override
  public List<TodoDto> findClosedTodo() {
    return mapper.returnDtoList(todoRepository.findAllByStatusTrueOrderByDeadline());
  }

  @Override
  public List<TodoDto> findAllTodos() {
    return mapper.returnDtoList(todoRepository.findAll());
  }

  @Override
  public void createTodo(TodoDto todoDto) {
    todoRepository.save(mapper.convertToEntity(todoDto));
  }
}

package com.todo.backend.service;

import com.todo.backend.domain.Todo;
import com.todo.backend.dto.TodoDto;
import com.todo.backend.dto.TodoUpdateDto;
import com.todo.backend.exception.NotFoundException;
import com.todo.backend.mapper.TodoMapper;
import com.todo.backend.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

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
        .orElseThrow(() -> new NotFoundException("Todo not found"));
  }

  @Override
  public void createTodo(TodoDto todoDto) {
    todoRepository.save(mapper.convertToEntity(todoDto));
  }

  @Override
  public void deleteById(Long id) {
    todoRepository.deleteById(id);
  }

  @Override
  public TodoDto update(TodoUpdateDto todoDto) {
    Optional<Todo> todo = todoRepository.findById(todoDto.getId());
    if (todo.isPresent()) {
      todo.get().setTitle(todoDto.getTitle());
      todo.get().setDescription(todoDto.getDescription());
      todo.get().setDeadline(todoDto.getDeadline());
      todo.get().setIsFinished(todoDto.getIsFinished());
      todoRepository.save(todo.get());
      return mapper.convertToDto(todo.get());
    } else {
      throw new NotFoundException("Todo not found");
    }
  }

  @Override
  public Page<TodoDto> findAllDynamicParameters(Integer page, Integer size, boolean status) {
    PageRequest request = PageRequest.of(page, size,  Sort.by("deadline").ascending());
    return todoRepository
            .findAllByIsFinished(request, status)
            .map(mapper::convertToDto);
  }
}

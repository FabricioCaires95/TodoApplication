package com.todo.backend.service;

import com.todo.backend.domain.Todo;
import com.todo.backend.dto.TodoDto;
import com.todo.backend.dto.TodoUpdateDto;
import com.todo.backend.exception.NotFoundException;
import com.todo.backend.mapper.TodoMapper;
import com.todo.backend.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class TodoServiceImpl implements TodoService {

  @Autowired
  private TodoRepository todoRepository;

  @Autowired
  private TodoMapper mapper;

  @Override
  @Cacheable(cacheNames = Todo.CACHE_NAME, key="#id")
  public TodoDto findById(Long id) {
    return todoRepository.findById(id)
            .map(todo -> mapper.convertToDto(todo))
            .orElseThrow(() -> new NotFoundException("Todo not found"));
  }

  @Override
  @CacheEvict(cacheNames = Todo.CACHE_NAME, allEntries = true)
  public void createTodo(TodoDto todoDto) {
    todoRepository.save(mapper.convertToEntity(todoDto));
  }

  @Override
  @CacheEvict(cacheNames = Todo.CACHE_NAME, key = "#id")
  public void deleteById(Long id) {
    todoRepository.deleteById(id);
  }

  @Override
  @CachePut(cacheNames = {Todo.CACHE_NAME, TodoUpdateDto.CACHE_NAME}, key = "#todoDto.id")
  public TodoUpdateDto update(TodoUpdateDto todoDto) {
    return todoRepository.findById(todoDto.getId()).map(todo -> {
      todoRepository.save(mapper.convertUpdateDtoToEntity(todoDto));
      return todoDto;
    }).orElseThrow(() -> new NotFoundException("Todo not found"));
  }

  @Override
  @Cacheable(cacheNames = Todo.CACHE_NAME, key = "#root.method.name")
  public Page<TodoDto> findAllDynamicParameters(Integer page, Integer size, boolean status) {
    PageRequest request = PageRequest.of(page, size,  Sort.by("deadline").ascending());
    return todoRepository
            .findAllByIsFinished(request, status)
            .map(mapper::convertToDto);
  }
}

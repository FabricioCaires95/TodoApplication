package com.todo.backend.service;


import com.todo.backend.dto.TodoCreateDto;
import com.todo.backend.dto.TodoDto;
import com.todo.backend.dto.TodoUpdateDto;
import com.todo.backend.exception.NotFoundException;
import com.todo.backend.mapper.TodoMapper;
import com.todo.backend.repository.TodoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Slf4j
public class TodoServiceImpl implements TodoService {

    private static final String REDIS_CACHE = "todoCache";

    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private TodoMapper mapper;

    @Override
    @Cacheable(value = REDIS_CACHE, key = "#id")
    public TodoDto findById(Long id) {
        return todoRepository.findById(id)
                .map(todo -> mapper.convertToDto(todo))
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
    @CacheEvict(value = REDIS_CACHE, key = "#todoDto.userId")
    public TodoDto update(TodoUpdateDto todoDto) {
        return mapper.convertToDto(todoRepository.save(mapper.convertUpdateDtoToEntity(todoDto)));
    }

    @Override
    @Cacheable(value = REDIS_CACHE)
    public Page<TodoDto> findAllByDynamicParameters(Integer page, Integer size, boolean status) {
        PageRequest request = PageRequest.of(page, size, Sort.by("deadline").ascending());
        return todoRepository
                .findAllByIsFinished(request, status)
                .map(mapper::convertToDto);
    }

    @Override
    public List<TodoDto> findAll() {
        return mapper.convertList(todoRepository.findAll());
    }

    @Override
    @Cacheable(value = REDIS_CACHE, key = "#userId" )
    public List<TodoDto> getTodosByUserId(Long userId) {
        return mapper.convertList(todoRepository.getTodosByUserIdAndIsFinished(userId, false));
    }

    @Override
    @CacheEvict(value = REDIS_CACHE, key = "#todoDto.idUser")
    public void createTodoForUser(TodoCreateDto todoDto) {
        todoRepository.save(mapper.convertCreateDtoToEntity(todoDto));
    }
}

package com.todo.backend.service;

import com.todo.backend.domain.Todo;
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

    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private TodoMapper mapper;

    @Autowired
    private UserService userService;

    @Override
    @Cacheable(cacheNames = Todo.CACHE_NAME, key = "#id")
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
    @CacheEvict(cacheNames = Todo.CACHE_NAME, allEntries = true)
    public void deleteById(Long id) {
        todoRepository.deleteById(id);
    }

    @Override
    @CacheEvict(cacheNames = Todo.CACHE_NAME, allEntries = true)
    public TodoDto update(TodoUpdateDto todoDto) {
        return todoRepository.findById(todoDto.getId()).map(todo -> mapper.
                convertToDto(todoRepository.save(
                        mapper.convertUpdateDtoToEntity(todoDto)))).orElseThrow(() -> new NotFoundException("Todo Not Found !"));
    }

    @Override
    @Cacheable(cacheNames = Todo.CACHE_NAME)
    public Page<TodoDto> findAllByDynamicParameters(Integer page, Integer size, boolean status) {
        PageRequest request = PageRequest.of(page, size, Sort.by("deadline").ascending());
        return todoRepository
                .findAllByIsFinished(request, status)
                .map(mapper::convertToDto);
    }

    @Override
    @Cacheable(cacheNames = Todo.CACHE_NAME)
    public List<TodoDto> findAll() {
        return mapper.convertList(todoRepository.findAll());
    }

    @Override
    public List<TodoDto> getTodosByUserId(Long userId) {
        return mapper.convertList(todoRepository.getTodosByUserIdAndIsFinished(userId, false));
    }

    @Override
    public void createTodoForUser(TodoCreateDto todoDto) {
        Todo todo = mapper.convertCreateDtoToEntity(todoDto);
        todo.setUser(userService.findUserEntity(todoDto.getIdUser()));
        todoRepository.save(todo);
    }
}

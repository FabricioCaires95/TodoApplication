package com.todo.backend.controller;

import com.todo.backend.dto.TodoDto;
import com.todo.backend.dto.TodoUpdateDto;
import com.todo.backend.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/todo")
public class TodoController {

  @Autowired
  private TodoService todoService;

  @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<TodoDto> findById(@PathVariable Long id) {
    return ResponseEntity.ok(todoService.findById(id));
  }

  @GetMapping("/open")
  public ResponseEntity<List<TodoDto>> findOpenTasks() {
    return ResponseEntity.ok(todoService.findOpenTodo());
  }

  @GetMapping("/close")
  public ResponseEntity<List<TodoDto>> findCloseTasks() {
    return ResponseEntity.ok(todoService.findClosedTodo());
  }

  @GetMapping("/all")
  public ResponseEntity<List<TodoDto>> findAllTasks() {
    return ResponseEntity.ok(todoService.findAllTodos());
  }

  @PostMapping("/create")
  public ResponseEntity<TodoDto> createTodo(
          @RequestBody @Valid TodoDto todoDto) {
    todoService.createTodo(todoDto);
    URI uri = ServletUriComponentsBuilder
            .fromCurrentRequest().path("/{id}")
            .buildAndExpand(todoDto.getId()).toUri();
    return ResponseEntity.created(uri).build();
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteTodo(@PathVariable Long id) {
    todoService.deleteById(id);
    return ResponseEntity.noContent().build();
  }

  @PutMapping("/update")
  public ResponseEntity<TodoUpdateDto> update(@RequestBody @Valid TodoUpdateDto todoDto) {
    todoService.update(todoDto);
    return ResponseEntity.noContent().build();
  }

}

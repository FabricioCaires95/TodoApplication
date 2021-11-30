package com.todo.backend.controller;

import com.todo.backend.dto.TodoDto;
import com.todo.backend.dto.TodoUpdateDto;
import com.todo.backend.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/todo")
public class TodoController {

  @Autowired
  private TodoService todoService;

  @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<TodoDto> findById(@PathVariable Long id) {
    return ResponseEntity.ok(todoService.findById(id));
  }

  @GetMapping("/all")
  public ResponseEntity<Page<TodoDto>> findAllTasksPageable(
          @RequestParam(value = "page", defaultValue = "0") Integer page,
          @RequestParam(value = "size", defaultValue = "3") Integer size,
          @RequestParam(value = "isFinished", defaultValue = "false") Boolean isFinished
  ) {
    return ResponseEntity.ok(todoService.findAllByDynamicParameters(page, size, isFinished));
  }

  @GetMapping(value = "/todosByUserId/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<TodoDto>> findAll(@PathVariable Long id) {
    return ResponseEntity.ok(todoService.getTodosByUserId(id));
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
  public ResponseEntity<TodoDto> update(@RequestBody @Valid TodoUpdateDto todoDto) {
      return ResponseEntity.ok().body(todoService.update(todoDto));
  }

}

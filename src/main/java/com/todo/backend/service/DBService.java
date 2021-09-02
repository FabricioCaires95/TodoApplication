package com.todo.backend.service;

import com.todo.backend.domain.Todo;
import com.todo.backend.repository.TodoRepository;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DBService {

  @Autowired
  private TodoRepository todoRepository;

  public void instanceDatabase() {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    Todo t1 = new Todo(null,
        "Study",
        "lerning English",
        LocalDateTime.parse("01/09/2021 19:50", formatter),
        false);

    todoRepository.saveAll(Arrays.asList(t1));
  }

}

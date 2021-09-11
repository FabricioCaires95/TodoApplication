package com.todo.backend.service;

import com.todo.backend.domain.Todo;
import com.todo.backend.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

@Service
public class DBService {

  @Autowired
  private TodoRepository todoRepository;

  public void instanceDatabase() {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    Todo t1 = new Todo(null,
        "Study",
        "lerning English",
            LocalDate.parse("01/09/2021", formatter),
        false);

    Todo t2 = new Todo(null,
            "Play",
            "Play Soccer",
            LocalDate.parse("05/09/2021", formatter),
            true);

    Todo t3 = new Todo(null,
            "Eat",
            "Make Dinner",
            LocalDate.parse("03/09/2021", formatter),
            false);

    todoRepository.saveAll(Arrays.asList(t1,t2,t3));
  }

}

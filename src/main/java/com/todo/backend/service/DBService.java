package com.todo.backend.service;

import com.todo.backend.domain.Todo;
import com.todo.backend.domain.User;
import com.todo.backend.repository.TodoRepository;
import com.todo.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

@Service
public class DBService {

  @Autowired
  private TodoRepository todoRepository;

  @Autowired
  private UserRepository userRepository;

  public void instanceDatabase() {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    User user = new User(
            1L, "legend","legend22@gmail.com", "23131eqw", null);

    Todo t1 = new Todo(null,
        "Study",
        "lerning English",
            LocalDate.parse("15/09/2021", formatter),
        true, user);

    Todo t2 = new Todo(null,
            "Play",
            "Play Soccer",
            LocalDate.parse("05/09/2021", formatter),
            false, user);

    userRepository.save(user);
    todoRepository.saveAll(Arrays.asList(t1,t2));
  }

}

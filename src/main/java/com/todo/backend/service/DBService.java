package com.todo.backend.service;

import com.todo.backend.domain.Todo;
import com.todo.backend.domain.User;
import com.todo.backend.enums.Roles;
import com.todo.backend.repository.TodoRepository;
import com.todo.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

  @Autowired
  private BCryptPasswordEncoder encoder;


  public void instanceDatabase() {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    User user1 = new User();
    user1.setName("Fabricio");
    user1.setEmail("fabricio@gmail.com");
    user1.setPassword(encoder.encode("julia123"));

    User user = new User(
            1L, "legend","legend22@gmail.com", encoder.encode("pet798"), null);

    user.addRole(Roles.ADMIN);

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

    userRepository.saveAll(Arrays.asList(user, user1));
    todoRepository.saveAll(Arrays.asList(t1,t2));
  }

}

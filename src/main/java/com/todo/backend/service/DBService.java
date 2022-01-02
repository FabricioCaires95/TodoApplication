package com.todo.backend.service;

import com.todo.backend.domain.Role;
import com.todo.backend.domain.Todo;
import com.todo.backend.domain.User;
import com.todo.backend.repository.RoleRepository;
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

  @Autowired
  private RoleRepository roleRepository;

  public void instanceDatabase() {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    Role r1 = new Role();
    r1.setName("ROLE_USER");

    Role r2 = new Role();
    r2.setName("ROLE_ADMIN");

    User user = new User(
            1L, "legend","legend22@gmail.com", "{bcrypt}$2a$10$mfT7JIWtFSt2cDKtOT9MC.A1LhPZI8BDbmP74254576LXQIWiMpHW", null , null);



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

    roleRepository.saveAll(Arrays.asList(r1,r2));
    userRepository.save(user);
    todoRepository.saveAll(Arrays.asList(t1,t2));
  }

}

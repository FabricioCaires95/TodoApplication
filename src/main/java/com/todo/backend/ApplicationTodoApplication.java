package com.todo.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class ApplicationTodoApplication {

  public static void main(String[] args) {
    SpringApplication.run(ApplicationTodoApplication.class, args);
  }

}

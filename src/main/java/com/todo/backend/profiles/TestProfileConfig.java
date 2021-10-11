package com.todo.backend.profiles;

import com.todo.backend.service.DBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("test")
public class TestProfileConfig {

  @Autowired
  private DBService dbService;

  @Value("${spring.jpa.hibernate.ddl-auto}")
  private String strategy;

  @Bean
  public boolean instantiateDatabase() {

    if (!"create".equals(strategy)){
      return false;
    }
    dbService.instanceDatabase();

    return true;
  }
}

package com.todo.backend.profiles;

import com.todo.backend.service.DBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("test")
public class TestProfileConfig {

  @Autowired
  private DBService dbService;

  @Bean
  public boolean instanceDB() {
    this.dbService.instanceDatabase();
    return true;
  }
}

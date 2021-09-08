package com.todo.backend.controller;

import com.todo.backend.TodoSpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc
@TodoSpringBootTest
public abstract class BaseControllerTest extends AbstractTestNGSpringContextTests {

  @Autowired
  protected MockMvc mockMvc;
}

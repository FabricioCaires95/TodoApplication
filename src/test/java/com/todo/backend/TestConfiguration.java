package com.todo.backend;

import org.springframework.context.annotation.Profile;

@Profile(TestConfiguration.PROFILE_TEST)
public class TestConfiguration {

  public static final String PROFILE_TEST = "test";
}

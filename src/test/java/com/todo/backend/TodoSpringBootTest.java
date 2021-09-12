package com.todo.backend;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@SpringBootTest
@ContextConfiguration(classes = {TestConfiguration.class})
@ActiveProfiles(TestConfiguration.PROFILE_TEST)
public @interface TodoSpringBootTest {

}

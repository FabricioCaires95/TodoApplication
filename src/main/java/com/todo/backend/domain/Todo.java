package com.todo.backend.domain;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Todo implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String title;
  private String description;
  private LocalDateTime deadline;
  private Boolean status;

  public Todo() {
    super();
  }

  public Todo(Long id, String title, String description, LocalDateTime deadline,
      Boolean status) {
    super();
    this.id = id;
    this.title = title;
    this.description = description;
    this.deadline = deadline;
    this.status = status;
  }
}

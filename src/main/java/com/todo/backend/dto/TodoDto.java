package com.todo.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class TodoDto implements Serializable {

  private Long id;
  private String title;
  private String description;
  private LocalDateTime deadline;
  private Boolean status;
}

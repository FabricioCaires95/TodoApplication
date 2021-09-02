package com.todo.backend.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class TodoDto implements Serializable {

  private String title;
  private String description;
  private LocalDateTime deadline;
  private Boolean status;
}

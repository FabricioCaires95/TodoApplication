package com.todo.backend.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.time.LocalDate;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TodoDto implements Serializable {

  private Long id;

  @NotEmpty(message = "name is required")
  private String title;

  @NotEmpty(message = "description is required")
  private String description;

  @FutureOrPresent
  @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
  private LocalDate deadline;
  private Boolean status;
}

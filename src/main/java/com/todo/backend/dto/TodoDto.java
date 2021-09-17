package com.todo.backend.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
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
  @ApiModelProperty(example = "Go to Gym")
  private String title;

  @NotEmpty(message = "description is required")
  @ApiModelProperty(example = "Go to the Gym at least three times a week")
  private String description;

  @FutureOrPresent(message = "Invalide date")
  @JsonFormat(pattern = "dd/MM/yyyy")
  @ApiModelProperty(example = "25/09/2021")
  private LocalDate deadline;

  private Boolean isFinished;
}

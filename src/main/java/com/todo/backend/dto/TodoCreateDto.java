package com.todo.backend.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TodoCreateDto {

    private Long id;

    @NotEmpty(message = "title is required")
    @ApiModelProperty(example = "Go to Gym")
    private String title;

    @NotEmpty(message = "description is required")
    @ApiModelProperty(example = "Go to the Gym at least three times a week")
    private String description;

    @FutureOrPresent
    @JsonFormat(pattern = "dd/MM/yyyy")
    @ApiModelProperty(example = "25/09/2021")
    private LocalDate deadline;

    private Boolean isFinished;

    private Long idUser;
}

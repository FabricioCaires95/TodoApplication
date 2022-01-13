package com.todo.backend.dto;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto implements Serializable {
    private Long id;

    @NotEmpty(message = "Name is required")
    private String name;

    @Email(message = "Email is invalid")
    private String email;

    @NotEmpty(message = "Password cannot be empty")
    @Size(min = 6, message = "Password is too short")
    private String password;

    @JsonManagedReference
    //@JsonIgnore
    private Set<TodoDto> tasks = new HashSet<>();
}

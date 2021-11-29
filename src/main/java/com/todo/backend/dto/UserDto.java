package com.todo.backend.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private String name;
    private String email;
    private String password;

    //@JsonManagedReference
    @JsonIgnore
    private Set<TodoDto> tasks = new HashSet<>();
}

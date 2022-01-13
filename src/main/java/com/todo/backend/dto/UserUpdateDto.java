package com.todo.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserUpdateDto implements Serializable {

    @NotNull(message = "User id is required")
    private Long id;

    @NotEmpty(message = "Name is required")
    private String name;

    @Email(message = "Email is invalid")
    private String email;

    @NotEmpty(message = "Password cannot be empty")
    @Size(min = 6, message = "Password is too short")
    private String password;
}

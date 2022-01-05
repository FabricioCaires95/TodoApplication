package com.todo.backend.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class CredentialsDto implements Serializable {

    private String email;
    private String password;

    @Override
    public String toString() {
        return super.toString();
    }
}

package com.todo.backend.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import java.io.Serializable;

@Getter
@Setter
@Embeddable
public class UserRoleId implements Serializable {

    @JoinColumn(name = "userId")
    private Long userId;

    @JoinColumn(name = "roleId")
    private Long roleId;

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}

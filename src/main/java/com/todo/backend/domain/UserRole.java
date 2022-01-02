package com.todo.backend.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import java.io.Serializable;

@Getter
@Setter
@Entity
public class UserRole implements Serializable {

    @EmbeddedId
    private UserRoleId userRoleId;

    @ManyToOne
    @MapsId("roleId")
    private Role role;

    @ManyToOne
    @MapsId("userId")
    private User user;

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

}

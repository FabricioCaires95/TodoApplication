package com.todo.backend.enums;

import lombok.Getter;

@Getter
public enum Roles {

    USER(1,"ROLE_USER"),
    ADMIN(2,"ROLE_ADMIN");

    private final int cod;
    private final String description;

    private Roles(int cod, String description) {
        this.cod = cod;
        this.description = description;
    }

    public static Roles toEnumRoles(String description) {

        for (Roles roles: Roles.values()) {
            if (description.equals(roles.getDescription())) {
                return roles;
            }
        }
        throw new IllegalArgumentException("description invalid " + description);
    }
}

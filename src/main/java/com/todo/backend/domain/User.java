package com.todo.backend.domain;

import com.todo.backend.enums.Roles;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;



@Builder
@Entity
@Getter
@Setter
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true, length = 150)
    private String email;

    @Column(nullable = false)
    private String password;

    @OneToMany(mappedBy = "user")
    private Set<Todo> tasks = new HashSet<>();

    @ElementCollection
    @CollectionTable(name = "ROLES")
    private Set<String> userRoles = new HashSet<>();


    public User() {
        addRole(Roles.USER);
    }

    public User(Long id, String name, String email, String password, Set<Todo> tasks) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.tasks = tasks;
        addRole(Roles.USER);
    }

    public User(Long id, String name, String email, String password, Set<Todo> tasks, Set<String> roles) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.tasks = tasks;
        this.setUserRoles(roles);
    }


    public Set<Roles> getUserRoles() {
        return userRoles.stream().map(Roles::toEnumRoles).collect(Collectors.toSet());
    }

    public void addRole(Roles roles) {
        userRoles.add(roles.getDescription());
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}

package com.todo.backend.security;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;

@Builder
@Getter
@Setter
public class UserSecurityDto implements UserDetails {

    private Long id;
    private String username;
    private String password;
    private Set<GrantedAuthority> authorites;

    public UserSecurityDto() {
    }

    public UserSecurityDto(Long id, String username, String password, Set<GrantedAuthority> authorites) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.authorites = authorites;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorites;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String toString() {
        return "UserSecurityDto{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", authorites=" + authorites +
                '}';
    }
}

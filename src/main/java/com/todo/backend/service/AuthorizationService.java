package com.todo.backend.service;

import com.todo.backend.exception.AuthorizationException;
import com.todo.backend.security.UserSecurityDto;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthorizationService {

    public UserSecurityDto getUserAuthenticated() {
        try {
            return (UserSecurityDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (Exception e) {
            throw new AuthorizationException("Access Denied");
        }
    }

    public void isAuthorizated(Long userId) {
        UserSecurityDto user = getUserAuthenticated();
        if (!user.getId().equals(userId)) {
            throw new AuthorizationException("Access Denied");
        }
    }
}

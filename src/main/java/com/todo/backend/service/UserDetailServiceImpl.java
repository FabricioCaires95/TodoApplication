package com.todo.backend.service;

import com.todo.backend.repository.UserRepository;
import com.todo.backend.security.UserSecurityDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Slf4j
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        log.info("Authenticating...");
        var user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(email));
        log.info("Authenticathed user: {}", user);
        log.info("user roles: {}", user.getUserRoles());
        return new UserSecurityDto(user.getId(), user.getEmail(), user.getPassword(), user.getUserRoles());

    }
}

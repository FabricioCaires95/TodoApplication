package com.todo.backend.service;

import com.todo.backend.repository.UserRepository;
import com.todo.backend.security.UserSecurityDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        var user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("UserName Not Found"));
        UserSecurityDto userSecurityDto = new UserSecurityDto();
        user.getUserRoles().forEach(role -> userSecurityDto.getAuthorites().add(new SimpleGrantedAuthority(role.getRole().getName())));
        userSecurityDto.setId(user.getId());
        userSecurityDto.setUsername(user.getEmail());
        userSecurityDto.setPassword(user.getPassword());
        return userSecurityDto;

    }
}

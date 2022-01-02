package com.todo.backend.service;

import com.todo.backend.domain.UserRole;
import com.todo.backend.repository.UserRepository;
import com.todo.backend.security.UserSecurityDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;

@Service
@Slf4j
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        log.info("Authenticating...");
        var user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("UserName Not Found"));
        UserSecurityDto userSecurityDto = new UserSecurityDto();
        Set<GrantedAuthority> authoritySet = new HashSet<>();

        for (UserRole userRole: user.getUserRoles()){
            authoritySet.add(new SimpleGrantedAuthority(userRole.getRole().getName()));
        }

        log.info("Authorites {}", authoritySet);

        userSecurityDto.setId(user.getId());
        userSecurityDto.setUsername(user.getEmail());
        userSecurityDto.setPassword(user.getPassword());
        userSecurityDto.setAuthorites(authoritySet);
        log.info("User Authenticated {}", userSecurityDto.toString());
        return userSecurityDto;

    }
}

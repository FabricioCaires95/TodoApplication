package com.todo.backend.security;

import com.todo.backend.service.UserDetailServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Slf4j
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private Environment environment;

    @Autowired
    private UserDetailServiceImpl userDetailService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        if (Arrays.asList(environment.getActiveProfiles()).contains("dev")) {
            http.headers().frameOptions().disable();
        }
        http.cors().and().csrf().disable();
        http.authorizeRequests()
                .antMatchers("/h2-console/**").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();

    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        PasswordEncoder passwordEncoderFactories = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        log.info("Password Encode: {} ", passwordEncoderFactories.encode("23131eqw"));
        /**auth.inMemoryAuthentication()
                .withUser("spacer")
                .password(passwordEncoderFactories.encode("teste1234"))
                .roles("USER"); **/

        auth.userDetailsService(userDetailService).passwordEncoder(passwordEncoderFactories);

    }
}

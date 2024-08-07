package com.babak.springboot.security;

import com.babak.springboot.security.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Author: Babak Behzadi
 * Email: behzadi.babak@gmail.com
 **/
@Configuration
@EnableWebSecurity
@EnableGlobalAuthentication
public class BaseSecurityConfig {

    @Value("${base.security.excluded.urls}")
    private String[] excludedUrls;
    private final UserService userService;

    public BaseSecurityConfig(UserService userService) {
        this.userService = userService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(excludedUrls).permitAll()
                        .anyRequest().authenticated()
                ).build();
    }
}

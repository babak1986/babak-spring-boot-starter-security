package com.babak.springboot.security;

import com.babak.springboot.security.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
        List<String> excludes = new ArrayList<>();
        excludes.add("/base/security/login");
        excludes.add("/base/security/logout");
        excludes.add("/base/security/validate");
        if (excludedUrls != null) {
            Arrays.asList(excludedUrls).forEach(s -> excludes.add(s));
        }

        return http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(request -> {
                    CorsConfiguration corsConfiguration = new CorsConfiguration();
                    corsConfiguration.addAllowedOriginPattern("/**");
                    corsConfiguration.addAllowedOrigin(CorsConfiguration.ALL);
                    corsConfiguration.addAllowedMethod(CorsConfiguration.ALL);
                    corsConfiguration.addAllowedHeader(CorsConfiguration.ALL);
                    return corsConfiguration;
                }))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(excludes.toArray(String[]::new)).permitAll()
                        .anyRequest().authenticated()
                ).build();
    }
}

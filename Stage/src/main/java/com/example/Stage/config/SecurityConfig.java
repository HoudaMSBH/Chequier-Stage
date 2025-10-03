package com.example.Stage.config;

import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // désactive CSRF pour tester
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll() // autorise toutes les requêtes
                )
                .formLogin(login -> login.disable()) // désactive le login par défaut
                .httpBasic(httpBasic -> httpBasic.disable()); // désactive basic auth

        return http.build();
    }
}

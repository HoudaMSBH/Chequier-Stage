 package com.example.Stage.auth_service.auth;


import com.example.Stage.entity.User;
import com.example.Stage.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

 @Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("🔍 Looking for user: '" + username + "'");


       User user = userRepository.findByUsername(username)
                .orElseThrow();


        System.out.println("✅ User found: " + user.getUsername());
        System.out.println("🔐 Password length: " + user.getPassword().length()); // Safe way to check

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .build();
    }
}


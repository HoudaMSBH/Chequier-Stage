package com.example.Stage;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordGenerator {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String hash = encoder.encode("PASSPASS");
        System.out.println("Mot de passe hashé : " + hash);
    }
}

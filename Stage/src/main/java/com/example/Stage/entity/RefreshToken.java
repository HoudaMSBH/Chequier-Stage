package com.example.Stage.entity;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String token;

    @OneToOne
    private User utilisateur;

    @Column(nullable = false)
    private Instant expiryDate;

    // Getters & setters
}

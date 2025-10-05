package com.example.Stage.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    // hash bcrypt
    @Column(nullable = false)
    private String password;

    // lien vers Banquier (un utilisateur correspond à un banquier)
    @OneToOne
    @JoinColumn(name = "id_banquier")
    private Banquier banquier;
}

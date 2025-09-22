package com.example.Stage.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "Client")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_client;

    private String nom;

    private String email;

    private Boolean black_listed;

    @ManyToOne
    @JoinColumn(name = "id_agence")
    private Agence agence;

    @OneToMany(mappedBy = "client")
    private List<Compte> comptes;

    @OneToMany(mappedBy = "client")
    private List<DemandeChequier> demandes;

    @OneToMany(mappedBy = "client")
    private List<Notification> notifications;
}

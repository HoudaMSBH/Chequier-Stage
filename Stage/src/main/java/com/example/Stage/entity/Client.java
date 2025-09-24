package com.example.Stage.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "Client")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_client")
    private Integer idClient;

    private String nom;

    private String email;

    @Column(name = "black_listed")
    private boolean blackListed;

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

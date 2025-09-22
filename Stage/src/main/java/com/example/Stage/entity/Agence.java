package com.example.Stage.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "Agence")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Agence {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_agence;

    private String nom_agence;

    private String adresse_agence;

    @OneToMany(mappedBy = "agence")
    private List<Client> clients;

    @OneToMany(mappedBy = "agence")
    private List<Banquier> banquiers;

    @OneToMany(mappedBy = "agence")
    private List<DemandeChequier> demandes;
}

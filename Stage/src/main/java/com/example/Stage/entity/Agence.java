package com.example.Stage.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "Agence")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Agence {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_agence")
    private Integer idAgence;

    @Column(name = "nom_agence")
    private String nomAgence;

    @Column(name = "adresse_agence")
    private String adresseAgence;

    @OneToMany(mappedBy = "agence")
    private List<Client> clients;

    @OneToMany(mappedBy = "agence")
    private List<Banquier> banquiers;

    @OneToMany(mappedBy = "agence")
    private List<DemandeChequier> demandes;
}

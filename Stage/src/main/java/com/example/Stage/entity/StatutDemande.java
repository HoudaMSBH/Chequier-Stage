package com.example.Stage.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "Statut_Demande")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatutDemande {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_statut;

    private String libelle;

    private Integer ordre;

    @OneToMany(mappedBy = "statut")
    private List<DemandeChequier> demandes;

    @OneToMany(mappedBy = "statut")
    private List<HistoriqueDemande> historiques;
}

package com.example.Stage.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "Statut_Demande")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class StatutDemande {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_statut")
    private Integer idStatut;

    private String libelle;

    private Integer ordre;

    @OneToMany(mappedBy = "statut")
    private List<DemandeChequier> demandes;

    @OneToMany(mappedBy = "statut")
    private List<HistoriqueDemande> historiques;
}

package com.example.Stage.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "Historique_Demande")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HistoriqueDemande {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_historique;

    private LocalDateTime date_changement;

    @ManyToOne
    @JoinColumn(name = "id_demande")
    private DemandeChequier demande;

    @ManyToOne
    @JoinColumn(name = "id_statut")
    private StatutDemande statut;

    @ManyToOne
    @JoinColumn(name = "id_banquier")
    private Banquier banquier; // nullable

    @ManyToOne
    @JoinColumn(name = "id_motif")
    private MotifRefus motif; // nullable

    private String motif_libelle;

    private String type_motif; // AUTO ou BANQUIER

    private String motif_libre;
}

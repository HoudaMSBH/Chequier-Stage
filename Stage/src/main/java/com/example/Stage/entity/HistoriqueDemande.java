package com.example.Stage.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "Historique_Demande")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class HistoriqueDemande {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_historique")
    private Integer idHistorique;

    @Column(name = "date_changement")
    private LocalDateTime dateChangement;

    @ManyToOne
    @JoinColumn(name = "id_demande")
    private DemandeChequier demande;

    @ManyToOne
    @JoinColumn(name = "id_statut")
    private StatutDemande statut;

    @ManyToOne
    @JoinColumn(name = "id_banquier")
    private Banquier banquier;

    @ManyToOne
    @JoinColumn(name = "id_motif")
    private MotifRefus motif;

    @Column(name = "motif_libelle")
    private String motifLibelle;

    @Column(name = "type_motif")
    private String typeMotif;

    @Column(name = "motif_libre", columnDefinition = "TEXT")
    private String motifLibre;
}

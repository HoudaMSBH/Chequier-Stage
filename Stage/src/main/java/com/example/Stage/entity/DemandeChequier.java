package com.example.Stage.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "Demande_Chequier")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DemandeChequier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_demande;

    private LocalDateTime date_demande;

    private Integer type_chequier; // 25 ou 50

    private Integer nombre_chequiers;

    @ManyToOne
    @JoinColumn(name = "id_client")
    private Client client;

    @ManyToOne
    @JoinColumn(name = "id_compte")
    private Compte compte;

    @ManyToOne
    @JoinColumn(name = "id_agence")
    private Agence agence;

    @ManyToOne
    @JoinColumn(name = "id_statut")
    private StatutDemande statut;

    @OneToMany(mappedBy = "demande")
    private List<HistoriqueDemande> historiques;

    @OneToMany(mappedBy = "demande")
    private List<Notification> notifications;
}

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
@Getter
@Setter

public class DemandeChequier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_demande")
    private Integer idDemande;

    @Column(name = "date_demande")
    private LocalDateTime dateDemande;

    @Column(name = "type_chequier")
    private Integer typeChequier;

    @Column(name = "nombre_chequiers")
    private Integer nombreChequiers;

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
}

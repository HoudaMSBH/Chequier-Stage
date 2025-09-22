package com.example.Stage.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "Motif_Refus")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MotifRefus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_motif;

    private String libelle;

    private String type_motif; // AUTO ou BANQUIER

    @OneToMany(mappedBy = "motif")
    private List<HistoriqueDemande> historiques;
}

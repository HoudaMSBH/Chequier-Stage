package com.example.Stage.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "Motif_Refus")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MotifRefus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_motif")
    private Integer idMotif;

    private String libelle;

    @Column(name = "type_motif")
    private String typeMotif; // AUTO ou BANQUIER

    @OneToMany(mappedBy = "motif")
    private List<HistoriqueDemande> historiques;
}

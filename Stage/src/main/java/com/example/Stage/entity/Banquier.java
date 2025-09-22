package com.example.Stage.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "Banquier")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Banquier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_banquier;

    private String nom;

    private String email;

    @ManyToOne
    @JoinColumn(name = "id_agence")
    private Agence agence;

    @OneToMany(mappedBy = "banquier")
    private List<HistoriqueDemande> historiques;
}

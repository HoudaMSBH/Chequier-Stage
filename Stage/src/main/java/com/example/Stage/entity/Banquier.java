package com.example.Stage.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "Banquier")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Banquier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_banquier")
    private Integer idBanquier;

    private String nom;

    private String email;

    @ManyToOne
    @JoinColumn(name = "id_agence")
    private Agence agence;

    @OneToMany(mappedBy = "banquier")
    private List<HistoriqueDemande> historiques;
}

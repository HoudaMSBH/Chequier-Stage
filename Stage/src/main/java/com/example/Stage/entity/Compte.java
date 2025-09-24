package com.example.Stage.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "Compte")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Compte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_compte")
    private Integer idCompte;

    @Column(name = "numero_compte")
    private String numeroCompte;

    private BigDecimal solde;

    private Boolean bloque;

    @ManyToOne
    @JoinColumn(name = "id_client")
    private Client client;

    @OneToMany(mappedBy = "compte")
    private List<DemandeChequier> demandes;
}

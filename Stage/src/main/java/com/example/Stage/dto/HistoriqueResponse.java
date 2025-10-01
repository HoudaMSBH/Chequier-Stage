package com.example.Stage.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HistoriqueResponse {
    private Integer idHistorique;
    private LocalDateTime dateChangement;
    private Integer idDemande;
    private String libStatut;
    private String banquierNom;
    private String motifLibelle;
    private String typeMotif;
    private String motifLibre;
}

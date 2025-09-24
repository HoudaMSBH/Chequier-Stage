package com.example.Stage.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DemandeChequierResponse {
    private Integer demandeId;
    private String clientNom;
    private String numeroCompte;
    private String typeChequier;
    private String agenceNom;
    private String statut;
    private boolean success;   // true si OK, false si refusé
    private String message;    // message explicatif
}

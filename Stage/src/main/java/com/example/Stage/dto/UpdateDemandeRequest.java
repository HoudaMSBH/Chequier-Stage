package com.example.Stage.dto;

import lombok.Data;

@Data
public class UpdateDemandeRequest {
    private Integer demandeId;
    private String action; // "VALIDER", "REFUSER", "CHANGER_STATUT"
    private Integer motifId; // si refus, l'id du motif choisi
    private String motifLibre; // si refus et autre motif
}

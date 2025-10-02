package com.example.Stage.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder // Permet d'utiliser DemandeChequierResponse.builder()
@NoArgsConstructor
@AllArgsConstructor // constructeur public avec tous les champs
public class DemandeChequierResponse {
    private Integer demandeId;
    private String clientNom;
    private String numeroCompte;
    private String typeChequier;
    private Integer nombreChequiers;
    private String agenceNom;
    private String statut;
    private boolean success;   // true si OK, false si refusé
    private String message;    // message explicatif
    private String reason;      // ex: "Client blacklisté"
    private String reasonType;
    private String clientEmail;
    private Boolean clientBlackListed;
    private BigDecimal solde;
    private Boolean compteBloque;
    private String agenceClient;
    private LocalDateTime dateDemande;
}

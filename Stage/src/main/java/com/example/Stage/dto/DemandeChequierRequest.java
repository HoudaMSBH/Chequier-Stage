package com.example.Stage.dto;

import lombok.Data;

@Data
public class DemandeChequierRequest {
    private Integer clientId;           // ID du client (saisi)
    private String numeroCompte;        // Numéro du compte (saisi)
    private String typeChequier;        // "25" ou "50"
    private boolean utiliserAgenceClient; // true = agence du client, false = autre agence
    private String nomAgenceChoisie;    // Nom de l’agence (si != agence du client)
}

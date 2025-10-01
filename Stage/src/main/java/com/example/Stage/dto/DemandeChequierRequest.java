package com.example.Stage.dto;

import lombok.Data;

@Data
public class DemandeChequierRequest {
    private Integer clientId;
    private String numeroCompte;
    private String typeChequier;        // "25" ou "50"
    private Integer nombreChequiers;
    private boolean utiliserAgenceClient;
    private String nomAgenceChoisie;
}

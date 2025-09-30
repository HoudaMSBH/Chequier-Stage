package com.example.Stage.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientResponse {
    private Integer id;
    private String nom;
    private String email;
    private boolean blackListed;
    private Integer idAgence;
    private String nomAgence;
}

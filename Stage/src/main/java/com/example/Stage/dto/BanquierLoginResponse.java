package com.example.Stage.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BanquierLoginResponse {
    private Long banquierId;
    private String nom;
    private String email;
    private String agence;
}

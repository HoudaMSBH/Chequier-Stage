
package com.example.Stage.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DashboardStatsResponse {
    private long demandesEnAttente;
    private long demandesRefusees;
    private long demandesValidees; // englobe Commande, Prêt, Remis
    private long demandesRemises;
    private long totalClients;
}

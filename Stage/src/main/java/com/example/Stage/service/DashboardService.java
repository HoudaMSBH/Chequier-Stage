package com.example.Stage.service;

import com.example.Stage.dto.DashboardStatsResponse;
import com.example.Stage.repository.ClientRepository;
import com.example.Stage.repository.DemandeChequierRepository;
import com.example.Stage.repository.HistoriqueDemandeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final DemandeChequierRepository demandeRepo;
    private final ClientRepository clientRepo;
    private final HistoriqueDemandeRepository historiqueDemandeRepository;

    public DashboardStatsResponse getStats() {
        long enAttente = demandeRepo.countEnAttente();
        long refuseesBanquier = demandeRepo.countRefuseesBanquier();
        long refuseesAuto = historiqueDemandeRepository.countRefuseesAuto();
        long totalRefusees = refuseesBanquier + refuseesAuto;

        long validees = demandeRepo.countValidees();
        long remises = demandeRepo.countRemises();
        long clients = clientRepo.count();

        return new DashboardStatsResponse(enAttente, totalRefusees, validees, remises, clients);
    }
}

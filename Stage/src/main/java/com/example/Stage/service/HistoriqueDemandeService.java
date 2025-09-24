package com.example.Stage.service;

import com.example.Stage.entity.HistoriqueDemande;
import com.example.Stage.repository.HistoriqueDemandeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HistoriqueDemandeService {

    private final HistoriqueDemandeRepository historiqueRepo;

    public HistoriqueDemandeService(HistoriqueDemandeRepository historiqueRepo) {
        this.historiqueRepo = historiqueRepo;
    }

    public List<HistoriqueDemande> getAllHistorique() {
        return historiqueRepo.findAll();
    }

    public HistoriqueDemande saveHistorique(HistoriqueDemande historique) {
        return historiqueRepo.save(historique);
    }
}

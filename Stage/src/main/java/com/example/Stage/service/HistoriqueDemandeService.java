package com.example.Stage.service;

import com.example.Stage.dto.HistoriqueResponse;
import com.example.Stage.entity.HistoriqueDemande;
import com.example.Stage.repository.HistoriqueDemandeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HistoriqueDemandeService {

    private final HistoriqueDemandeRepository historiqueDemandeRepository;

    public List<HistoriqueResponse> getAllHistorique() {
        return historiqueDemandeRepository.findAll()
                .stream()
                .map(this::mapHistorique)
                .collect(Collectors.toList());
    }

    private HistoriqueResponse mapHistorique(HistoriqueDemande h) {
        return new HistoriqueResponse(
                h.getIdHistorique(),
                h.getDateChangement(),
                h.getDemande() != null ? h.getDemande().getIdDemande() : null,
                h.getStatut() != null ? h.getStatut().getLibelle() : null,
                h.getBanquier() != null ? h.getBanquier().getNom() : null,
                h.getMotifLibelle(),
                h.getTypeMotif(),
                h.getMotifLibre()
        );
    }
}

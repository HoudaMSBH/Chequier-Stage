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

    private final HistoriqueDemandeRepository historiqueRepo;

    public List<HistoriqueResponse> getAllHistorique() {
        return historiqueRepo.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    private HistoriqueResponse mapToDto(HistoriqueDemande h) {
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

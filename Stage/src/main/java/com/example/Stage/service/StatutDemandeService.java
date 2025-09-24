package com.example.Stage.service;

import com.example.Stage.entity.StatutDemande;
import com.example.Stage.repository.StatutDemandeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StatutDemandeService {

    private final StatutDemandeRepository statutRepo;

    public StatutDemandeService(StatutDemandeRepository statutRepo) {
        this.statutRepo = statutRepo;
    }

    public List<StatutDemande> getAllStatuts() {
        return statutRepo.findAll();
    }

    public Optional<StatutDemande> getStatutByLibelle(String libelle) {
        return statutRepo.findByLibelle(libelle);
    }
}

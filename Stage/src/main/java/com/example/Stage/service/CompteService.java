package com.example.Stage.service;

import com.example.Stage.entity.Compte;
import com.example.Stage.repository.CompteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompteService {

    private final CompteRepository compteRepository;

    public CompteService(CompteRepository compteRepository) {
        this.compteRepository = compteRepository;
    }

    public List<Compte> getAllComptes() {
        return compteRepository.findAll();
    }

    public Optional<Compte> getCompteById(Integer id) {
        return compteRepository.findById(id);
    }

    public Compte saveCompte(Compte compte) {
        return compteRepository.save(compte);
    }

    public void deleteCompte(Integer id) {
        compteRepository.deleteById(id);
    }
}

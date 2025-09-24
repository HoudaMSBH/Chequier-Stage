package com.example.Stage.service;

import com.example.Stage.entity.Agence;
import com.example.Stage.repository.AgenceRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AgenceService {

    private final AgenceRepository agenceRepository;

    public AgenceService(AgenceRepository agenceRepository) {
        this.agenceRepository = agenceRepository;
    }

    public List<Agence> getAllAgences() {
        return agenceRepository.findAll();
    }

    public Optional<Agence> getAgenceById(Integer id) {
        return agenceRepository.findById(id);
    }

    public Agence saveAgence(Agence agence) {
        return agenceRepository.save(agence);
    }

    public void deleteAgence(Integer id) {
        agenceRepository.deleteById(id);
    }
}

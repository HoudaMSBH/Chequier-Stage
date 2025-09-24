package com.example.Stage.service;

import com.example.Stage.entity.MotifRefus;
import com.example.Stage.repository.MotifRefusRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MotifRefusService {

    private final MotifRefusRepository motifRepo;

    public MotifRefusService(MotifRefusRepository motifRepo) {
        this.motifRepo = motifRepo;
    }

    public List<MotifRefus> getAllMotifs() {
        return motifRepo.findAll();
    }
}

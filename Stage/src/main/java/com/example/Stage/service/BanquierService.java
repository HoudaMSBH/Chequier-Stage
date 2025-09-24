package com.example.Stage.service;

import com.example.Stage.entity.Banquier;
import com.example.Stage.repository.BanquierRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BanquierService {

    private final BanquierRepository banquierRepository;

    public BanquierService(BanquierRepository banquierRepository) {
        this.banquierRepository = banquierRepository;
    }

    public List<Banquier> getAllBanquiers() {
        return banquierRepository.findAll();
    }

    public Optional<Banquier> getBanquierById(Integer id) {
        return banquierRepository.findById(id);
    }

    public Banquier saveBanquier(Banquier banquier) {
        return banquierRepository.save(banquier);
    }

    public void deleteBanquier(Integer id) {
        banquierRepository.deleteById(id);
    }
}

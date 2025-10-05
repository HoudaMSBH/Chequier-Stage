package com.example.Stage.repository;

import com.example.Stage.entity.Banquier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BanquierRepository extends JpaRepository<Banquier, Integer> {
    Optional<Banquier> findByNom(String nom);
    Optional<Banquier> findByEmail(String email);
}

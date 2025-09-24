package com.example.Stage.repository;

import com.example.Stage.entity.StatutDemande;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StatutDemandeRepository extends JpaRepository<StatutDemande, Integer> {
    //recherche par libellé
    Optional<StatutDemande> findByLibelle(String libelle);
}

package com.example.Stage.repository;

import com.example.Stage.entity.HistoriqueDemande;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoriqueDemandeRepository extends JpaRepository<HistoriqueDemande, Integer> {
    @Query("SELECT COUNT(h) FROM HistoriqueDemande h WHERE h.statut.libelle = 'Refusé auto'")
    long countRefuseesAuto();
}

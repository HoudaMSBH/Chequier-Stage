package com.example.Stage.repository;

import com.example.Stage.entity.DemandeChequier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface DemandeChequierRepository extends JpaRepository<DemandeChequier, Integer> {
    @Query("SELECT d FROM DemandeChequier d WHERE d.client.idClient = :clientId")
    List<DemandeChequier> findByClientId(@Param("clientId") Integer clientId);//left// join / join

    @Query("SELECT COUNT(d) FROM DemandeChequier d WHERE d.statut.libelle = 'En attente'")
    long countEnAttente();

    @Query("SELECT COUNT(d) FROM DemandeChequier d WHERE d.statut.libelle = 'Refusé banquier'")
    long countRefuseesBanquier();


    @Query("SELECT COUNT(d) FROM DemandeChequier d WHERE d.statut.libelle IN ('Commandé', 'Prêt', 'Remis')")
    long countValidees();

    @Query("SELECT COUNT(d) FROM DemandeChequier d WHERE d.statut.libelle = 'Remis'")
    long countRemises();
}

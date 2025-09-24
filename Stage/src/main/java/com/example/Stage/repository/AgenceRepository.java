package com.example.Stage.repository;

import com.example.Stage.entity.Agence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;


@Repository
public interface AgenceRepository extends JpaRepository<Agence, Integer> {
    Optional<Agence> findByNomAgence(String nomAgence);
}


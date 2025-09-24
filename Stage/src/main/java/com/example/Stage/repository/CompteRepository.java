package com.example.Stage.repository;

import com.example.Stage.entity.Compte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.*;
@Repository
public interface CompteRepository extends JpaRepository<Compte, Integer> {
    Optional<Compte> findByNumeroCompte(String numeroCompte);

}

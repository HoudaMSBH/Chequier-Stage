package com.example.Stage.repository;

import com.example.Stage.entity.MotifRefus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.*;
@Repository
public interface MotifRefusRepository extends JpaRepository<MotifRefus, Integer> {
    List<MotifRefus> findByTypeMotif(String typeMotif);
}

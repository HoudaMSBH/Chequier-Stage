package com.example.Stage.repository;

import com.example.Stage.entity.Banquier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BanquierRepository extends JpaRepository<Banquier, Integer> {
}

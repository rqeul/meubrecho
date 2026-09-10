package com.meubrecho.repository;

import com.meubrecho.model.Pronome;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PronomeRepository extends JpaRepository<Pronome, Long> {
}
package com.raquel.meubrecho.repository;

import com.raquel.meubrecho.model.Peca;
import com.raquel.meubrecho.model.enums.Categoria;
import com.raquel.meubrecho.model.enums.StatusPeca;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PecaRepository extends JpaRepository<Peca, Long> {


    List<Peca> findByCategoriaAndStatus(Categoria categoria, StatusPeca status);

    List<Peca> findByCategoria(Categoria categoria);

    List<Peca> findByStatus(StatusPeca status);

    List<Peca> findByUserId(Long userId);


}
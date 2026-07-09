package com.meubrecho.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_categoria")
@Data
@NoArgsConstructor
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Colocamos unique = true para não ter duas categorias "Calçados" no banco
    @Column(nullable = false, unique = true)
    private String nome;


}
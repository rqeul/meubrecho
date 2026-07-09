package com.meubrecho.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_tamanho")
@Data
@NoArgsConstructor
public class Tamanho {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Colocamos unique = true para não ter dois "GG" no banco
    @Column(nullable = false, unique = true)
    private String size;
}
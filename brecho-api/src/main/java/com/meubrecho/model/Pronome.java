package com.meubrecho.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_pronome")
@Data
@NoArgsConstructor
public class Pronome {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Colocamos unique = true para garantir opções únicas (ex: "Ela/Dela")
    @Column(nullable = false, unique = true)
    private String pronomes;
}
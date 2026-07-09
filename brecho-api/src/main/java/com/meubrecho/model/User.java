package com.meubrecho.model;

import com.meubrecho.model.enums.TipoUser;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_user")
@Data
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private boolean ativo = true;


    @Column(nullable = false)
    private String nomeCompleto;

    @Column(nullable = false, unique = true)
    private String email;


    @Column(nullable = false, unique = true)
    private String cpf;


    @Column(nullable = false)
    private String senha;

    @Column(nullable = false)
    private String telefone;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoUser tipo; // Ex: CLIENTE, FORNECEDOR, AMBOS

    // --- CHAVES ESTRANGEIRAS ---

    @ManyToOne
    @JoinColumn(name = "pronome_id")
    private Pronome pronome;


    private Endereco endereco;
}
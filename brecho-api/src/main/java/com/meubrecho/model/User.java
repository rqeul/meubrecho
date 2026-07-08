package com.meubrecho.model;

import com.meubrecho.model.enums.TipoUser;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import com.meubrecho.model.enums.Pronomes;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nomeCompleto;

    @Column(nullable = false, unique = true, length = 14)
    private String cpf;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String senha;

    private String telefone;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoUser tipo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Pronomes pronomes; // O campo inclusivo e maravilhoso que você adicionou!

    @Embedded
    private Endereco endereco;

    @Column(nullable = false)
    @Builder.Default
    private Boolean ativo = true;


    @OneToMany(mappedBy = "cliente")
    @Builder.Default
    private List<Pedido> pedidos = new ArrayList<>();


    @OneToMany(mappedBy = "user")
    @Builder.Default
    private List<Peca> pecasConsignadas = new ArrayList<>();
}
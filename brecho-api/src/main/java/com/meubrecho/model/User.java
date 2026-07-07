package com.seubrecho.api.model;

import jakarta.persistence.*;
import lombok.Data;
import com.raquel.meubrecho.model.enums.TipoUsuario;
import com.raquel.meubrecho.model.enums.Pronomes;
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
    private TipoUsuario tipo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Pronomes pronomes; // O campo inclusivo e maravilhoso que você adicionou!

    @Embedded
    private Endereco endereco;


    @OneToMany(mappedBy = "cliente")
    private List<Pedido> pedidos = new ArrayList<>();


    @OneToMany(mappedBy = "user")
    private List<Peca> pecasConsignadas = new ArrayList<>();
}
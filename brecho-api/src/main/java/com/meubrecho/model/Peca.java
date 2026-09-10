package com.meubrecho.model;

import com.meubrecho.model.enums.EstadoConservacao;
import com.meubrecho.model.enums.StatusPeca;
import com.meubrecho.model.enums.TipoPosse;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Entity
@Table(name = "tb_peca")
@Data
@NoArgsConstructor
public class Peca {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(nullable = false)
    private String titulo;

    @Column(columnDefinition = "TEXT")
    private String descricao;

    @Column(nullable = false)
    private BigDecimal preco;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoConservacao estadoConservacao;

    private String detalhesAvaria;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusPeca status; // DISPONIVEL, VENDIDA

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoPosse tipoPosse; // ACERVO_PROPRIO ou CONSIGNADA

    // Porcentagem que fica com a fornecedora (Ex: 0.50 para 50%)
    @Column(precision = 3, scale = 2)
    private BigDecimal porcentagemRepasse;

    // --- CHAVES ESTRANGEIRAS ---


    @ManyToOne
    @JoinColumn(name = "categoria_id", nullable = false)
    private Categoria categoria;


    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name="tamanho_id",nullable = false)
    private Tamanho tamanho;

    @ManyToOne
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;

}
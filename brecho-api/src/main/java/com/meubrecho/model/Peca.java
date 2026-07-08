package com.meubrecho.model;

import com.meubrecho.model.enums.StatusPeca;
import com.meubrecho.model.enums.TipoPosse;
import com.meubrecho.model.enums.Categoria;
import com.meubrecho.model.enums.EstadoConservacao;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="tb_peca")
public class Peca {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titulo;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String descricao;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Categoria categoria;

    @Column(nullable = false)
    private String tamanho;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_conservacao", nullable = false)
    private EstadoConservacao estadoConservacao;

    @Column(name = "detalhes_avaria", columnDefinition = "TEXT")
    private String detalhesAvaria;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal preco;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusPeca status = StatusPeca.DISPONIVEL;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_posse", nullable = false)
    private TipoPosse tipoPosse;

    @Column(name = "porcentagem_repasse", precision = 3, scale = 2)
    private BigDecimal porcentagemRepasse;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;
}
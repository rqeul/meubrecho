package com.meubrecho.model;

import com.meubrecho.model.enums.StatusPedido;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="tb_pedido")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Corrigido para GenerationType
    private Long id;

    @Column(name="valor_total", nullable = false)
    private BigDecimal valorTotal;

    @Column(name="valor_frete", nullable = false)
    private BigDecimal valorFrete;

    @Column(name ="codigo_rastreio", nullable = false)
    private String codigoRastreio;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private StatusPedido status = StatusPedido.ABERTO; // Boa prática: já nascer aberto!

    @OneToMany(mappedBy = "pedido", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Peca> pecas;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private User cliente;

}
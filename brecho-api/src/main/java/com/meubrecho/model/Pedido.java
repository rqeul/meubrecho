package com.meubrecho.model;

import com.meubrecho.model.enums.StatusPedido;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "tb_pedido")
@Data
@NoArgsConstructor
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal valorTotal;
    private BigDecimal valorFrete;
    private String codigoRastreio;

    @Enumerated(EnumType.STRING)
    private StatusPedido status = StatusPedido.ABERTO;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private User cliente;


    // O "mappedBy" avisa o banco: "Não crie tabela extra, a chave estrangeira
    // já está lá na entidade Peca, no atributo chamado 'pedido'
    @OneToMany(mappedBy = "pedido")
    private List<Peca> pecas;
}
package com.raquel.meubrecho.model;

import com.raquel.meubrecho.model.enums.StatusPeca;
import com.raquel.meubrecho.model.enums.TipoPosse;
import com.raquel.meubrecho.model.enums.Categoria;
import com.raquel.meubrecho.model.enums.EstadoConservacao;
import jakarta.persistence.*;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
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
    // precision = 10 (total de números permitidos), scale = 2 (números depois da vírgula)
    // Exemplo do limite: 99999999.99
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal preco;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default //Quando se usa o Builder, o Lombok ignora os valores default que foram atribuídos.
    //Pra contornar isso, usei a anotação @Builder.Default - agora sim ele vai reconhecer o default!!
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
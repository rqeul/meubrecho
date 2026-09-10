package com.meubrecho.model.dto.response;

import com.meubrecho.model.enums.StatusPedido;
import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

@Data
public class PedidoResponseDTO {

    private Long id;

    // Essencial para o front-end saber o que exibir (ex: botão de pagar ou código de rastreio)
    private StatusPedido status;

    private BigDecimal valorTotalPecas;
    private BigDecimal valorFrete;

    // Valor final cobrado no cartão (Peças + Frete)
    private BigDecimal valorTotal;

    // Para o Cliente acompanhar a entrega pelos Correios
    private String codigoRastreio;


    // Usamos o DTO da peça para garantir que os dados de Fornecedor continuem protegidos!
    private List<PecaResponseDTO> pecas;

    // Opcional: Apenas o nome do Cliente para colocar no cabeçalho ("Sacolinha da Raquel")
    private String nomeCliente;
}
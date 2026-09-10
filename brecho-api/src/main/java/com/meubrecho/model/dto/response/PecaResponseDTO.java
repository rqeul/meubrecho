package com.meubrecho.model.dto.response;

import com.meubrecho.model.Tamanho;
import com.meubrecho.model.enums.EstadoConservacao;
import lombok.Data;
import java.math.BigDecimal;

// DTO para exibir as roupas na vitrine
@Data
public class PecaResponseDTO {

    private Long id;
    private String titulo;
    private String descricao;
    private Tamanho tamanho;
    private String categoria; // Ao invés da entidade Categoria, mandamos só o nome ("Calçados")
    private BigDecimal preco;
    private EstadoConservacao estadoConservacao;
    private String detalhesAvaria;
    // Opcional: Podemos mostrar só o nome de Fornecedor, sem vazar os dados sensíveis da entidade
    private String nomeFornecedor;
}
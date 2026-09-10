package com.meubrecho.model.dto.request;

import com.meubrecho.model.enums.EstadoConservacao;
import com.meubrecho.model.enums.TipoPosse;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class PecaRequestDTO {

    @NotBlank(message = "Cadê o título dessa peça? Vou registrar como Labubu se você não preencher!")
    private String titulo;

    private String descricao;

    @NotNull(message = "Coloca o preço da roupa!")
    @Positive(message = "O preço não pode ser zero nem negativo.")
    private BigDecimal preco;

    @NotNull(message = "O estado de conservação da peça é obrigatório. Clientes não podem ter surpresas ruins!")
    private EstadoConservacao estadoConservacao;

    // Detalhes da avaria não é obrigatório, pois a roupa pode estar NOVA
    private String detalhesAvaria;

    @NotNull(message = "Informa pra mim: a peça é sua (acervo próprio) ou consignada?")
    private TipoPosse tipoPosse;

    // Pode ser nulo se for acervo próprio
    private BigDecimal porcentagemRepasse;

    // --- CHAVES ESTRANGEIRAS (Apenas os IDs) ---

    @NotNull(message = "A categoria é obrigatória.")
    private Long categoriaId;

    @NotNull(message = "Qual o tamanho?")
    private Long tamanhoId;

    @NotNull(message = "O ID de Fornecedor é obrigatório para registrar de quem é a peça.")
    private Long userId;
}
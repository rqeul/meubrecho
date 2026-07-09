package com.meubrecho.model.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.util.List;

@Data
public class PedidoRequestDTO {

    @NotNull(message = "O ID de quem está comprando é obrigatório.")
    private Long clienteId;

    @NotEmpty(message = "A sacolinha tá vazia! Escolha pelo menos uma peça :))")
    private List<Long> pecasIds;


}
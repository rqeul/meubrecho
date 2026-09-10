package com.meubrecho;

import com.meubrecho.model.Peca;
import com.meubrecho.model.dto.response.PecaResponseDTO;
import com.meubrecho.model.dto.response.PedidoResponseDTO;
import com.meubrecho.model.dto.response.UserResponseDTO;
import com.meubrecho.model.Pedido;
import com.meubrecho.model.User;

import java.math.BigDecimal;
import java.util.stream.Collectors;

public class DTOMapper {

    // 0. Traduzindo um USER para um USER DTO (Protegendo Senha e CPF)
    public static UserResponseDTO toUserDTO(User user) {
        if (user == null) return null;

        UserResponseDTO dto = new UserResponseDTO();
        dto.setId(user.getId());
        dto.setNomeCompleto(user.getNomeCompleto());
        dto.setEmail(user.getEmail());

        // Extraímos só a String do pronome, se existir
        if (user.getPronome() != null) {
            dto.setPronome(user.getPronome().getPronomes());
        }

        return dto;
    }

    // 1. Traduzindo uma PEÇA para um PEÇA DTO
    public static PecaResponseDTO toPecaDTO(Peca peca) {
        if (peca == null) return null;

        PecaResponseDTO dto = new PecaResponseDTO();
        dto.setId(peca.getId());
        dto.setTitulo(peca.getTitulo());
        dto.setDescricao(peca.getDescricao());
        dto.setTamanho(peca.getTamanho());
        dto.setPreco(peca.getPreco());
        dto.setEstadoConservacao(peca.getEstadoConservacao());
        dto.setDetalhesAvaria(peca.getDetalhesAvaria());

        // Em vez de mandar a entidade toda, extraímos só o nome em String!
        if (peca.getCategoria() != null) {
            dto.setCategoria(peca.getCategoria().getNome());
        }

        // Escondendo os dados sensíveis de Fornecedor
        if (peca.getUser() != null) {
            dto.setNomeFornecedor(peca.getUser().getNomeCompleto());
        }

        return dto;
    }

    // 2. Traduzindo um PEDIDO para um PEDIDO DTO
    public static PedidoResponseDTO toPedidoDTO(Pedido pedido) {
        if (pedido == null) return null;

        PedidoResponseDTO dto = new PedidoResponseDTO();
        dto.setId(pedido.getId());
        dto.setStatus(pedido.getStatus());
        dto.setValorFrete(pedido.getValorFrete());
        dto.setValorTotal(pedido.getValorTotal());
        dto.setCodigoRastreio(pedido.getCodigoRastreio());

        // Calculando o total só das peças para o DTO
        BigDecimal totalPecas = BigDecimal.ZERO;
        if (pedido.getPecas() != null) {
            for (Peca p : pedido.getPecas()) {
                totalPecas = totalPecas.add(p.getPreco());
            }

            dto.setPecas(pedido.getPecas().stream()
                    .map(DTOMapper::toPecaDTO)
                    .collect(Collectors.toList()));
        }
        dto.setValorTotalPecas(totalPecas);

        // Pegando só o nome de quem comprou (sem expor o CPF e senha)
        if (pedido.getCliente() != null) {
            dto.setNomeCliente(pedido.getCliente().getNomeCompleto());
        }

        return dto;
    }
}
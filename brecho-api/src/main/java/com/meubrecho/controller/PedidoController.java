package com.meubrecho.controller;

import com.meubrecho.model.dto.request.PedidoRequestDTO;
import com.meubrecho.model.dto.response.PedidoResponseDTO;
import com.meubrecho.DTOMapper;
import com.meubrecho.model.Pedido;
import com.meubrecho.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @PostMapping
    public ResponseEntity<PedidoResponseDTO> criarPedido(@RequestBody PedidoRequestDTO novoPedido) {
        Pedido pedidoCriado = pedidoService.criarPedido(novoPedido);

        return ResponseEntity.status(HttpStatus.CREATED).body(DTOMapper.toPedidoDTO(pedidoCriado));
    }

    @PutMapping("/{id}/pagar")
    public ResponseEntity<PedidoResponseDTO> confirmarPagamento(@PathVariable Long id) {
        Pedido pedidoPago = pedidoService.confirmarPagamento(id);

        return ResponseEntity.ok(DTOMapper.toPedidoDTO(pedidoPago));
    }

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<PedidoResponseDTO>> buscarHistorico(@PathVariable Long clienteId) {
        List<Pedido> historico = pedidoService.buscarHistoricoCliente(clienteId);

        List<PedidoResponseDTO> historicoDTO = historico.stream()
                .map(DTOMapper::toPedidoDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(historicoDTO);
    }
}
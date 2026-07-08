package com.meubrecho.controller;

import com.meubrecho.model.Pedido;
import com.meubrecho.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @PostMapping
    public ResponseEntity<Pedido> criarPedido(@RequestBody Pedido novoPedido) {
        Pedido pedidoCriado = pedidoService.criarPedido(novoPedido);
        return ResponseEntity.status(HttpStatus.CREATED).body(pedidoCriado);
    }

    @PutMapping("/{id}/pagar")
    public ResponseEntity<Pedido> confirmarPagamento(@PathVariable Long id) {
        Pedido pedidoPago = pedidoService.confirmarPagamento(id);
        return ResponseEntity.ok(pedidoPago);
    }

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<Pedido>> buscarHistorico(@PathVariable Long clienteId) {
        List<Pedido> historico = pedidoService.buscarHistoricoCliente(clienteId);
        return ResponseEntity.ok(historico);
    }
}

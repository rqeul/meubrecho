package com.meubrecho.service;

import com.meubrecho.model.Pedido;
import com.meubrecho.model.Peca;
import com.meubrecho.model.User;
import com.meubrecho.model.enums.StatusPedido;
import com.meubrecho.model.enums.StatusPeca;
import com.meubrecho.model.enums.TipoPosse;
import com.meubrecho.repository.PedidoRepository;
import com.meubrecho.repository.PecaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private PecaRepository pecaRepository;

    // 1. CRIAR A SACOLINHA (ABRIR PEDIDO)
    public Pedido criarPedido(Pedido novoPedido) {
        // Garante que o pedido nasça como ABERTO, independente do que vier da internet
        novoPedido.setStatus(StatusPedido.ABERTO);

        // Regra de Ouro: Só podemos vender peças que ainda estão na vitrine!
        for (Peca peca : novoPedido.getPecas()) {
            if (peca.getStatus() != StatusPeca.DISPONIVEL) {
                throw new RuntimeException("Ops! A peça '" + peca.getTitulo() + "' já foi vendida para outra pessoa sortuda!");
            }
        }

        // Calcula o valor total somando o preço das roupas
        BigDecimal totalPecas = BigDecimal.ZERO;
        for (Peca peca : novoPedido.getPecas()) {
            // Em BigDecimal, a gente soma usando o ".add()"
            totalPecas = totalPecas.add(peca.getPreco());
        }

        // Soma o frete ao total das roupas e guarda no pedido
        novoPedido.setValorTotal(totalPecas.add(novoPedido.getValorFrete()));

        // Salva a sacolinha no banco de dados
        return pedidoRepository.save(novoPedido);
    }

    // 2. PAGAMENTO APROVADO
    public Pedido confirmarPagamento(Long pedidoId) {
        // Busca o pedido. Se não achar, lança o erro.
        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado no sistema! :("));

        // Só podemos confirmar o pagamento de um pedido que não foi pago ainda
        if (pedido.getStatus() == StatusPedido.PAGO || pedido.getStatus() == StatusPedido.ENVIADO) {
            throw new RuntimeException("Esse pedido já foi pago! Agora fica de olho no status de envio <3");
        }


        pedido.setStatus(StatusPedido.PAGO);

        // Regra do Estoque Único: Tira as peças da vitrine!
        for (Peca peca : pedido.getPecas()) {
            // A peça agora é da cliente!
            peca.setStatus(StatusPeca.VENDIDA);

            // LÓGICA DE CONSIGNAÇÃO
            // Verificamos se a peça é consignada (ou seja, de uma fornecedora)
            if (peca.getTipoPosse() == TipoPosse.CONSIGNACAO) {

                // Em Java, quando multiplicamos BigDecimal, usamos o .multiply()
                // Aqui pegamos o preço da peça (Ex: 100.00) e multiplicamos pelo repasse (Ex: 0.50)
                BigDecimal valorRepasse = peca.getPreco().multiply(peca.getPorcentagemRepasse());


                User fornecedora = peca.getUser();


                // Por enquanto, vamos apenas dar um print no console.
                System.out.println("💰 REPASSE CALCULADO: A peça '" + peca.getTitulo() +
                        "' foi vendida! Separar R$ " + valorRepasse +
                        " para: " + fornecedora.getNomeCompleto());
            }

            // Salva a alteração da peça no banco de dados para ela sumir do site
            pecaRepository.save(peca);
        }

        // Salva a atualização do Pedido
        return pedidoRepository.save(pedido);
    }

    // 3. BUSCAR HISTÓRICO DO CLIENTE
    public List<Pedido> buscarHistoricoCliente(Long clienteId) {
        // Retorna todas as comprinhas que aquele User já fez
        return pedidoRepository.findByClienteId(clienteId);
    }
}
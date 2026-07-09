package com.meubrecho.service;

import com.meubrecho.model.Pedido;
import com.meubrecho.model.User;
import com.meubrecho.model.Peca;
import com.meubrecho.model.dto.request.PedidoRequestDTO;
import com.meubrecho.model.enums.StatusPedido;
import com.meubrecho.model.enums.StatusPeca;
import com.meubrecho.model.enums.TipoPosse;
import com.meubrecho.repository.PedidoRepository;
import com.meubrecho.repository.PecaRepository;
import com.meubrecho.repository.UserRepository;
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

    @Autowired
    private UserRepository userRepository;

    // --- 1. CRIAR A SACOLINHA (ABRIR PEDIDO) ---
    public Pedido criarPedido(PedidoRequestDTO dto) {

        // 1. Busca a cliente no banco usando o ID do Front-end
        User cliente = userRepository.findById(dto.getClienteId())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrada no sistema!"));

        // 2. Busca TODAS as peças de uma vez
        List<Peca> pecasCompradas = pecaRepository.findAllById(dto.getPecasIds());

        if (pecasCompradas.isEmpty()) {
            throw new RuntimeException("A sacolinha não pode estar vazia!");
        }

        BigDecimal totalPecas = BigDecimal.ZERO;

        // 3. Validação de estoque e soma segura
        for (Peca peca : pecasCompradas) {
            if (peca.getStatus() != StatusPeca.DISPONIVEL) {
                throw new RuntimeException("Ops! A peça '" + peca.getTitulo() + "' já foi vendida para outra pessoa sortuda!");
            }
            // Soma o preço real direto do banco
            totalPecas = totalPecas.add(peca.getPreco());
        }

        // 4. Monta a Entidade Pedido
        Pedido novoPedido = new Pedido();
        novoPedido.setCliente(cliente);
        novoPedido.setStatus(StatusPedido.ABERTO);

        // 5. Calcula o Frete de forma segura (no servidor)
        String cepDaCliente = cliente.getEndereco().getCep();
        BigDecimal freteCalculado = calcularFrete(cepDaCliente);

        novoPedido.setValorFrete(freteCalculado);

        // 6. Define o total absoluto
        novoPedido.setValorTotal(totalPecas.add(freteCalculado));

        // 7. Salva o pedido inicial no banco de dados
        Pedido pedidoSalvo = pedidoRepository.save(novoPedido);

        // 8. Atrela cada peça ao pedido e tira da vitrine
        for (Peca peca : pecasCompradas) {
            peca.setPedido(pedidoSalvo);
            // peca.setStatus(StatusPeca.VENDIDA); -> Opcional: Você pode mudar para VENDIDA aqui,
            // ou deixar para mudar apenas quando confirmar o pagamento (abaixo).
            pecaRepository.save(peca);
        }

        pedidoSalvo.setPecas(pecasCompradas);
        return pedidoSalvo;
    }

    // --- 2. PAGAMENTO APROVADO ---
    public Pedido confirmarPagamento(Long pedidoId) {
        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado no sistema! :("));

        if (pedido.getStatus() == StatusPedido.PAGO || pedido.getStatus() == StatusPedido.ENVIADO) {
            throw new RuntimeException("Esse pedido já foi pago! Agora fica de olho no status de envio <3");
        }

        pedido.setStatus(StatusPedido.PAGO);

        for (Peca peca : pedido.getPecas()) {
            // Se não mudou o status na criação do pedido, MUDA AQUI COM CERTEZA!
            peca.setStatus(StatusPeca.VENDIDA);

            // Lógica financeira para fornecedoras (Consignação)
            if (peca.getTipoPosse() == TipoPosse.CONSIGNACAO) {
                BigDecimal valorRepasse = peca.getPreco().multiply(peca.getPorcentagemRepasse());
                User fornecedora = peca.getUser();

                System.out.println("REPASSE CALCULADO: A peça '" + peca.getTitulo() +
                        "' foi vendida! Separar R$ " + valorRepasse +
                        " para: " + fornecedora.getNomeCompleto());
            }

            pecaRepository.save(peca);
        }

        return pedidoRepository.save(pedido);
    }

    // --- 3. BUSCAR HISTÓRICO DO CLIENTE ---
    public List<Pedido> buscarHistoricoCliente(Long clienteId) {
        return pedidoRepository.findByClienteId(clienteId);
    }

    // --- 4. MÉTODO AUXILIAR PARA CALCULAR O FRETE ---
    private BigDecimal calcularFrete(String cep) {
        if (cep == null || cep.isBlank()) {
            return new BigDecimal("25.00"); // Frete padrão de segurança
        }

        // Remove os tracinhos se a pessoa tiver digitado com traço
        String cepLimpo = cep.replace("-", "");

        // Região local fica mais em conta
        if (cepLimpo.startsWith("286")) {
            return new BigDecimal("10.00");
        }

        // Resto do Brasil
        return new BigDecimal("25.00");
    }
}
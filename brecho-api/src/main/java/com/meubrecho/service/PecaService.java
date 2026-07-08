package com.raquel.meubrecho.service;

import com.raquel.meubrecho.model.Peca;
import com.raquel.meubrecho.model.enums.Categoria;
import com.raquel.meubrecho.model.enums.StatusPeca;
import com.raquel.meubrecho.repository.PecaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class PecaService {

    @Autowired
    private PecaRepository pecaRepository;

    // 1. REGRA DE CADASTRO
    public Peca cadastrarPeca(Peca novaPeca) {

        return pecaRepository.save(novaPeca);
    }

    // 2. REGRA DA VITRINE INICIAL (O que a cliente vê quando entra no site)
    public List<Peca> buscarVitrine() {
        // A vitrine NUNCA mostra roupas já vendidas. Apenas o que tá DISPONÍVEL!
        return pecaRepository.findByStatus(StatusPeca.DISPONIVEL);
    }

    // 3. REGRA DO MENU DE CATEGORIAS (Ex: Clicou em "Calçados")
    public List<Peca> buscarPorCategoria(Categoria categoria, boolean apenasDisponiveis) {
        if (apenasDisponiveis) {
            // Traz apenas os calçados que ainda não foram vendidos
            return pecaRepository.findByCategoriaAndStatus(categoria, StatusPeca.DISPONIVEL);
        }
        // Se a pessoa quiser ver tudo (inclusive o que já foi vendido pra ter inspiração)
        return pecaRepository.findByCategoria(categoria);
    }

    // 4. REGRA DO PAINEL DA FORNECEDORA (Consignação)
    public List<Peca> buscarPecasDaFornecedora(Long userId) {
        // A fornecedora entra no perfil dela e vê todas as peças que ela deixou no brechó
        return pecaRepository.findByUserId(userId);
    }

    // 5. REGRA DE DETALHES DA PEÇA (Quando clica na foto)
    public Peca buscarPorId(Long id) {
        return pecaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Poxa, não encontramos essa peça no acervo! Ela pode ter sido removida."));
    }

    // 7. REGRA DE ATUALIZAÇÃO (Editar detalhes de uma peça)
    public Peca atualizarPeca(Long id, Peca pecaComNovosDados) {
        // 1. Buscamos a peça original que já está no banco de dados
        Peca pecaExistente = buscarPorId(id);

        // 2. Atualizamos apenas os campos que podem ser modificados pela usuária
        pecaExistente.setTitulo(pecaComNovosDados.getTitulo());
        pecaExistente.setDescricao(pecaComNovosDados.getDescricao());
        pecaExistente.setPreco(pecaComNovosDados.getPreco());
        pecaExistente.setTamanho(pecaComNovosDados.getTamanho());
        pecaExistente.setCategoria(pecaComNovosDados.getCategoria());
        pecaExistente.setEstadoConservacao(pecaComNovosDados.getEstadoConservacao());
        pecaExistente.setDetalhesAvaria(pecaComNovosDados.getDetalhesAvaria());

        // Nota: Perceba que NÃO atualizamos o ID, o Status e nem a Fornecedora (User).
        // Essas informações são sensíveis e só mudam com regras de negócio específicas!

        // 3. O JPA é inteligente: quando mandamos salvar uma peça que já tem ID,
        // ele sabe que é para ATUALIZAR (Update) e não criar uma nova.
        return pecaRepository.save(pecaExistente);
    }

    // 6. REGRA DE EXCLUSÃO (Quando a dona do brechó desiste de vender a peça)
    public void deletarPeca(Long id) {
        Peca peca = buscarPorId(id); // Reutilizamos o método de cima para garantir que a peça existe!

        // Aqui fazemos um Hard Delete (apagamos do banco).
        // Como a peça não foi vendida (não está em nenhum pedido finalizado), não tem problema apagar!
        pecaRepository.delete(peca);
    }
}
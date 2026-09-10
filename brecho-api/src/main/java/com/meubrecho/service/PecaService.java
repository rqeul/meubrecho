package com.meubrecho.service;

import com.meubrecho.model.Categoria;
import com.meubrecho.model.Peca;
import com.meubrecho.model.Tamanho;
import com.meubrecho.model.User;
import com.meubrecho.model.dto.request.PecaRequestDTO;
import com.meubrecho.model.enums.StatusPeca;
import com.meubrecho.repository.CategoriaRepository;
import com.meubrecho.repository.PecaRepository;
import com.meubrecho.repository.TamanhoRepository;
import com.meubrecho.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PecaService {

    @Autowired
    private PecaRepository pecaRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private TamanhoRepository tamanhoRepository;

    @Autowired
    private UserRepository userRepository;

    // 1. REGRA DE CADASTRO (Agora usando DTO)
    public Peca cadastrarPeca(PecaRequestDTO dto) {

        // Buscamos as entidades reais no banco usando os IDs do DTO
        Categoria categoria = categoriaRepository.findById(dto.getCategoriaId())
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada!"));

        Tamanho tamanho = tamanhoRepository.findById(dto.getTamanhoId())
                .orElseThrow(() -> new RuntimeException("Tamanho não encontrado!"));

        User fornecedora = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("Fornecedora não encontrada!"));

        // Criamos a peça vazia e populamos com os dados
        Peca novaPeca = new Peca();
        novaPeca.setTitulo(dto.getTitulo());
        novaPeca.setDescricao(dto.getDescricao());
        novaPeca.setPreco(dto.getPreco());
        novaPeca.setEstadoConservacao(dto.getEstadoConservacao());
        novaPeca.setDetalhesAvaria(dto.getDetalhesAvaria());
        novaPeca.setTipoPosse(dto.getTipoPosse());
        novaPeca.setPorcentagemRepasse(dto.getPorcentagemRepasse());

        // Associações
        novaPeca.setStatus(StatusPeca.DISPONIVEL);
        novaPeca.setCategoria(categoria);
        novaPeca.setTamanho(tamanho);
        novaPeca.setUser(fornecedora);

        return pecaRepository.save(novaPeca);
    }

    // 2. REGRA DA VITRINE INICIAL
    public List<Peca> buscarVitrine() {
        return pecaRepository.findByStatus(StatusPeca.DISPONIVEL);
    }

    // 3. REGRA DO MENU DE CATEGORIAS
    public List<Peca> buscarPorCategoria(Long categoriaId, boolean apenasDisponiveis) {
        if (apenasDisponiveis) {
            return pecaRepository.findByCategoriaIdAndStatus(categoriaId, StatusPeca.DISPONIVEL);
        }
        return pecaRepository.findByCategoriaId(categoriaId);
    }

    // 4. REGRA DO PAINEL DA FORNECEDORA
    public List<Peca> buscarPecasDaFornecedora(Long userId) {
        return pecaRepository.findByUserId(userId);
    }

    // 5. REGRA DE DETALHES DA PEÇA
    public Peca buscarPorId(Long id) {
        return pecaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Poxa, não encontramos essa peça no acervo! Ela pode ter sido removida."));
    }

    // 7. REGRA DE ATUALIZAÇÃO (Corrigida para usar as buscas de Repositório)
    public Peca atualizarPeca(Long id, PecaRequestDTO pecaComNovosDados) {

        // 1. Buscamos a peça original
        Peca pecaExistente = buscarPorId(id);

        // 2. Buscamos as novas categorias e tamanhos baseados nos IDs fornecidos no DTO
        Categoria novaCategoria = categoriaRepository.findById(pecaComNovosDados.getCategoriaId())
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada!"));

        Tamanho novoTamanho = tamanhoRepository.findById(pecaComNovosDados.getTamanhoId())
                .orElseThrow(() -> new RuntimeException("Tamanho não encontrado!"));

        // 3. Atualizamos a peça existente
        pecaExistente.setTitulo(pecaComNovosDados.getTitulo());
        pecaExistente.setDescricao(pecaComNovosDados.getDescricao());
        pecaExistente.setPreco(pecaComNovosDados.getPreco());
        pecaExistente.setEstadoConservacao(pecaComNovosDados.getEstadoConservacao());
        pecaExistente.setDetalhesAvaria(pecaComNovosDados.getDetalhesAvaria());

        // Agora sim, passando o objeto inteiro!
        pecaExistente.setTamanho(novoTamanho);
        pecaExistente.setCategoria(novaCategoria);

        // 4. Salvamos (O Hibernate faz um UPDATE automático porque a peça já tem ID)
        return pecaRepository.save(pecaExistente);
    }

    // 6. REGRA DE EXCLUSÃO
    public void deletarPeca(Long id) {
        Peca peca = buscarPorId(id);
        pecaRepository.delete(peca);
    }
}
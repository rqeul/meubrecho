package com.meubrecho.config;

import com.meubrecho.model.*;
import com.meubrecho.model.enums.*;
import com.meubrecho.repository.PecaRepository;
import com.meubrecho.repository.UserRepository;
import com.meubrecho.repository.CategoriaRepository;
import com.meubrecho.repository.PronomeRepository;
import com.meubrecho.repository.TamanhoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import java.math.BigDecimal;
import java.util.Arrays;

@Configuration
public class TestConfig implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PecaRepository pecaRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private PronomeRepository pronomeRepository;

    @Autowired
    private TamanhoRepository tamanhoRepository;

    @Override
    public void run(String... args) throws Exception {

        // 1. Criar e salvar os Tamanhos
        Tamanho pp = new Tamanho();
        pp.setSize("PP");
        Tamanho p = new Tamanho();
        p.setSize("P");
        tamanhoRepository.saveAll(Arrays.asList(pp, p));

        // 2. Criar e salvar as Categorias
        Categoria parteDeCima = new Categoria();
        parteDeCima.setNome("Parte de Cima");
        Categoria parteDeBaixo = new Categoria();
        parteDeBaixo.setNome("Parte de Baixo");
        categoriaRepository.saveAll(Arrays.asList(parteDeCima, parteDeBaixo));

        // 3. Criar e salvar o Pronome
        Pronome pronomeEla = new Pronome();
        pronomeEla.setPronomes("Ela/Dela");
        pronomeRepository.save(pronomeEla);

        Endereco endRaquel = new Endereco();
        endRaquel.setCep("28600-000");
        endRaquel.setLogradouro("Rua das Flores");
        endRaquel.setNumero("123");
        endRaquel.setBairro("Centro");
        endRaquel.setCidade("Nova Friburgo");
        endRaquel.setUf("RJ");

        // 4. Usuária com as suas novas credenciais blindadas
        User raquel = new User();
        raquel.setNomeCompleto("Raquel (Dona do Brechó)");
        raquel.setCpf("17130974788");
        raquel.setEmail("raquel@meubrecho.com");
        raquel.setSenha("25042005Raquel!");
        raquel.setTelefone("22999999999");
        raquel.setTipo(TipoUser.AMBOS);
        raquel.setPronome(pronomeEla);
        raquel.setEndereco(endRaquel);

        userRepository.save(raquel);

        // 5. Salvando as peças
        Peca peca1 = new Peca();
        peca1.setTitulo("Calça Levis 501 Vintage");
        peca1.setDescricao("Descrição...");
        peca1.setCategoria(parteDeBaixo);
        peca1.setTamanho(p);
        peca1.setEstadoConservacao(EstadoConservacao.SEMINOVA);
        peca1.setPreco(new BigDecimal("120.00"));
        peca1.setStatus(StatusPeca.DISPONIVEL);
        peca1.setTipoPosse(TipoPosse.ACERVO_PROPRIO);
        peca1.setUser(raquel);

        Peca peca2 = new Peca();
        peca2.setTitulo("Blusinha Farm Cropped");
        peca2.setDescricao("Descrição...");
        peca2.setCategoria(parteDeCima);
        peca2.setTamanho(p);
        peca2.setEstadoConservacao(EstadoConservacao.COM_MARCAS);
        peca2.setDetalhesAvaria("Pequena manchinha");
        peca2.setPreco(new BigDecimal("45.00"));
        peca2.setStatus(StatusPeca.DISPONIVEL);
        peca2.setTipoPosse(TipoPosse.CONSIGNACAO);
        peca2.setPorcentagemRepasse(new BigDecimal("0.50"));
        peca2.setUser(raquel);

        pecaRepository.saveAll(Arrays.asList(peca1, peca2));

        System.out.println("Banco alimentado com sucesso <3");
    }
}
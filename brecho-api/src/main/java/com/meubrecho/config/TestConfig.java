package com.meubrecho.config;

import com.meubrecho.model.Endereco;
import com.meubrecho.model.Peca;
import com.meubrecho.model.User;
import com.meubrecho.model.enums.*;
import com.meubrecho.repository.PecaRepository;
import com.meubrecho.repository.UserRepository;
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

    @Override
    public void run(String... args) throws Exception {

        // Endereço usando construtor vazio e setters
        Endereco endRaquel = new Endereco();
        endRaquel.setCep("28600-000");
        endRaquel.setLogradouro("Rua das Flores");
        endRaquel.setNumero("123");
        endRaquel.setBairro("Centro");
        endRaquel.setCidade("Nova Friburgo");
        endRaquel.setUf("RJ");

        // Usuário usando construtor vazio e setters
        User raquel = new User();
        raquel.setNomeCompleto("Raquel (Dona do Brechó)");
        raquel.setCpf("11122233344");
        raquel.setEmail("raquel@meubrecho.com");
        raquel.setSenha("senha123");
        raquel.setTelefone("22999999999");
        raquel.setTipo(TipoUser.AMBOS);
        raquel.setPronomes(Pronomes.ELA_DELA);
        raquel.setEndereco(endRaquel);

        userRepository.save(raquel);

        // Peças usando construtor vazio e setters
        Peca peca1 = new Peca();
        peca1.setTitulo("Calça Levis 501 Vintage");
        peca1.setDescricao("Descrição...");
        peca1.setCategoria(Categoria.PARTE_DE_BAIXO);
        peca1.setTamanho("38");
        peca1.setEstadoConservacao(EstadoConservacao.SEMINOVA);
        peca1.setPreco(new BigDecimal("120.00"));
        peca1.setStatus(StatusPeca.DISPONIVEL);
        peca1.setTipoPosse(TipoPosse.ACERVO_PROPRIO);
        peca1.setUser(raquel);

        Peca peca2 = new Peca();
        peca2.setTitulo("Blusinha Farm Cropped");
        peca2.setDescricao("Descrição...");
        peca2.setCategoria(Categoria.PARTE_DE_CIMA);
        peca2.setTamanho("M");
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
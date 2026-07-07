package com.raquel.meubrecho.config;

import com.raquel.meubrecho.model.Endereco;
import com.raquel.meubrecho.model.Peca;
import com.raquel.meubrecho.model.User;
import com.raquel.meubrecho.model.enums.*;
import com.raquel.meubrecho.repository.PecaRepository;
import com.raquel.meubrecho.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.util.Arrays;

// @Configuration avisa ao Spring: "Leia isso aqui quando o sistema ligar!"
@Configuration
public class TestConfig implements CommandLineRunner {

    // @Autowired injeta (traz) os estoquistas para dentro desta classe
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PecaRepository pecaRepository;

    //Sem esquecer que estou implementando uma interface, então preciso reescrever esse método obrigatório:
    @Override
    public void run(String... args) throws Exception {

        // Criando a primeira usuária, que sou eu, utilizando o padrão Builder
        Endereco endRaquel = Endereco.builder()
                .cep("28600-001")
                .logradouro("Avenida Alberto Braune")
                .numero("33")
                .bairro("Centro")
                .cidade("Nova Friburgo")
                .uf("RJ")
                .build();

        User raquel = User.builder()
                .nomeCompleto("Raquel (Dona)")
                .cpf("17130974788")
                .email("raquelss2005@gmail.com")
                .senha("Senha123!")
                .telefone("24999934131")
                .tipo(TipoUser.AMBOS)
                .pronomes(Pronomes.ELA_DELA)
                .endereco(endRaquel)
                .build(); // O build() finaliza a montagem e "fecha o pacote"

        // Mando o Repository me salvar (kkkk) no banco
        userRepository.save(raquel);

        //Criando algumas peças fictícias pra visualizar na tela
        Peca peca1 = Peca.builder()
                .titulo("Calça Levis 501 Vintage")
                .descricao("Calça jeans clássica, cintura alta, lavagem clara. Sem avarias.")
                .categoria(Categoria.PARTE_DE_BAIXO)
                .tamanho("38")
                .estadoConservacao(EstadoConservacao.SEMINOVO_PERFEITO)
                .preco(new BigDecimal("120.00"))
                .tipoPosse(TipoPosse.ACERVO_PROPRIO)
                .user(raquel)
                .build();

        Peca peca2 = Peca.builder()
                .titulo("Blusinha Farm Cropped")
                .descricao("Cropped florido de viscose, perfeito pro verão.")
                .categoria(Categoria.PARTE_DE_CIMA)
                .tamanho("M")
                .estadoConservacao(EstadoConservacao.MARCAS_DE_USO)
                .detalhesAvaria("Pequena manchinha na manga direita, quase imperceptível.")
                .preco(new BigDecimal("45.00"))
                .tipoPosse(TipoPosse.CONSIGNACAO)
                .porcentagemRepasse(new BigDecimal("0.50"))
                .user(raquel)
                .build();

        Peca peca3 = Peca.builder()
                .titulo("Bota Coturno Tratorada")
                .descricao("Coturno preto de couro sintético.")
                .categoria(Categoria.CALCADOS)
                .tamanho("36")
                .estadoConservacao(EstadoConservacao.NOVO_COM_ETIQUETA)
                .preco(new BigDecimal("150.00"))
                .tipoPosse(TipoPosse.ACERVO_PROPRIO)
                .user(raquel)
                .build();

        // Salvando todas as peças de uma vez só!
        pecaRepository.saveAll(Arrays.asList(peca1, peca2, peca3));

        System.out.println("Banco de dados alimentado com sucesso <3");
    }
}
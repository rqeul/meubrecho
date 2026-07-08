package com.meubrecho.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// @Embeddable: Diz ao Spring que esta classe não vai virar uma tabela própria,
// mas que as suas colunas vão ser injetadas diretamente na tabela que a chamar (tb_user).
@Embeddable
@Data
@Builder
@NoArgsConstructor // Construtor vazio (Obrigatório para o JPA)
@AllArgsConstructor // Construtor com todos os argumentos (Obrigatório para o Builder)
public class Endereco {



    @Column(name = "endereco_cep", length = 9)
    private String cep;

    @Column(name = "endereco_logradouro")
    private String logradouro;

    @Column(name = "endereco_numero")
    private String numero;

    @Column(name = "endereco_complemento")
    private String complemento;

    @Column(name = "endereco_bairro")
    private String bairro;

    @Column(name = "endereco_cidade")
    private String cidade;

    @Column(name = "endereco_uf", length = 2)
    private String uf;

}
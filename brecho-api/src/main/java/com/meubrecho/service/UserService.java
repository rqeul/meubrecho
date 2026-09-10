package com.meubrecho.service;

import com.meubrecho.model.Pronome;
import com.meubrecho.model.User;
import com.meubrecho.model.dto.request.UserRequestDTO;
import com.meubrecho.model.enums.TipoUser;
import com.meubrecho.repository.PronomeRepository;
import com.meubrecho.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Precisamos injetar o repositório de Pronome para buscar o ID que veio do front!
    @Autowired
    private PronomeRepository pronomeRepository;

    public User cadastrarUser(UserRequestDTO dto) {

        // 1. Buscamos o pronome no banco de dados usando o ID que veio no DTO
        Pronome pronomeEscolhido = pronomeRepository.findById(dto.getPronomeId())
                .orElseThrow(() -> new RuntimeException("Pronome não encontrado!"));

        // 2. Criamos a Entidade vazia
        User novoUser = new User();

        // 3. Copiamos os dados permitidos do DTO para a Entidade
        novoUser.setNomeCompleto(dto.getNomeCompleto());
        novoUser.setEmail(dto.getEmail());
        novoUser.setCpf(dto.getCpf());
        novoUser.setSenha(dto.getSenha()); // Idealmente aqui entrará a criptografia (BCrypt) no futuro!
        novoUser.setTelefone(dto.getTelefone());

        // 4. Setamos os dados que o sistema controla (Regra de Negócio)
        novoUser.setAtivo(true); // Toda conta nasce ativa
        novoUser.setTipo(TipoUser.CLIENTE); // Exemplo: Todo mundo nasce cliente via web
        novoUser.setPronome(pronomeEscolhido); // Ligando a Chave Estrangeira com segurança!

        // 5. Salvamos no banco
        return userRepository.save(novoUser);
    }

    public void inativarUser(Long id) {
        // 1. Busca o usuário pelo ID (reutilizando o método de busca)
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado para inativação!"));

        // 2. Muda o status para false
        user.setAtivo(false);

        // 3. Salva a alteração (o Hibernate faz o UPDATE automático)
        userRepository.save(user);

        System.out.println("Usuário " + user.getNomeCompleto() + " inativado com sucesso!");
    }
}
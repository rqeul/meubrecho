package com.meubrecho.service;

import com.meubrecho.model.User;
import com.meubrecho.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

// @Service avisa ao Spring: "Aqui ficam as regras de negócio de usuários"
@Service
public class UserService {

    // Injetando o nosso "Estoquista" para acessar o banco de dados
    @Autowired
    private UserRepository userRepository;

    //Regex para validar a senha
    // Pelo menos 1 minúscula, 1 maiúscula, 1 número, 1 caractere especial e mínimo de 8 caracteres.
    private static final String SENHA_FORTE_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";

    //REGRAS DE CADASTRO
    public User cadastrarUser(User novoUser) {

        if (userRepository.existsByEmail(novoUser.getEmail()))
            throw new RuntimeException("Esse email já tá cadastrado no nosso sistema, xuxu!! Será que você já não tem uma conta? Clique em 'esqueci a senha'");

        if (userRepository.existsByCpf(novoUser.getCpf()))
            throw new RuntimeException("É permitido apenas 1 CPF por conta, e já tenho esse CPF registrado no sistema! :/");

        if (!senhaEhSegura(novoUser.getSenha()))
            throw new RuntimeException("Bora deixar sua conta mais segura! A senha deve ter no mínimo 8 caracteres, contendo pelo menos uma letra maiúscula, uma minúscula, um número e um caractere especial.");

        //Passando por todas as verificações, estamos prontos pra salvar o novo User!
        return userRepository.save(novoUser);
    }

    //REGRA DE EXCLUSÃO DE CONTA
    public void inativarUser(Long id) {
        // Se o User não existir, o orElseThrow já joga o erro na tela.
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Oops! Não encontramos nenhuma conta com esse ID para inativar."));

        // 2. A exclusão suave (Soft Delete): Apenas inativamos a conta.
        // Assim, os pedidos e as peças consignadas dela continuam seguros no banco!
        user.setAtivo(false);

        // 3. Salvamos a usuária atualizada de volta no banco.
        userRepository.save(user);
    }



    //private porque uso a função como auxiliar APENAS dessa classe
    private boolean senhaEhSegura(String senha) {
        if (senha == null) {
            return false;
        }
        // O Java usa a classe Pattern para bater a senha da pessoa com a fórmula Regex
        return Pattern.compile(SENHA_FORTE_REGEX).matcher(senha).matches();
    }
}
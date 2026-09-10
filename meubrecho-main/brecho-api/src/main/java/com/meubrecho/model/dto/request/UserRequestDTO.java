package com.meubrecho.model.dto.request;

import com.meubrecho.validation.Cpf;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class UserRequestDTO {

    @NotBlank(message = "Digita o seu nome completo pra gente! <3")
    private String nomeCompleto;


    @Email(message = "Esse e-mail não parece ser válido :/ Vê se não ficou faltando um @, por exemplo.")
    @NotBlank(message = "O e-mail é obrigatório para você criar sua conta e começar suas comprinhas <3")
    private String email;

    @Cpf
    @NotBlank(message= "Relaxa que aqui ninguém vai te cadastrar como Mesário! O preenchimento do CPF é obrigatório.")
    private String cpf;

    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[\\W_]).{8,}$",
            message = "Amore, a senha deve ter no mínimo 8 caracteres, contendo pelo menos uma letra maiúscula, uma minúscula, um número e um caractere especial. Só pra deixar sua conta bem segura!"
    )
    private String senha;

    @NotBlank(message = "O telefone de contato é obrigatório.")
    private String telefone;

    private Long pronomeId; // recebemos apenas o ID em vez da entidade inteira
}
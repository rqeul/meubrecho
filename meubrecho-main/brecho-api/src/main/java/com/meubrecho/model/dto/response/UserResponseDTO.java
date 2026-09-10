package com.meubrecho.model.dto.response;

import lombok.Data;

// DTO para enviar dados pro React SEM VAZAR senha ou CPF
@Data
public class UserResponseDTO {

    private Long id;
    private String nomeCompleto;
    private String email;
    private String pronome; // Em vez de mandar a tabela inteira de pronome, mandamos só a String ("Ela/Dela")

}
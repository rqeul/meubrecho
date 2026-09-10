package com.meubrecho.controller;

import com.meubrecho.DTOMapper;
import com.meubrecho.model.dto.request.UserRequestDTO;
import com.meubrecho.model.dto.response.UserResponseDTO;
import com.meubrecho.model.User;
import com.meubrecho.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<UserResponseDTO> cadastrarUser(@Valid @RequestBody UserRequestDTO novoUser) {
        User userSalvo = userService.cadastrarUser(novoUser);
        // Retorna apenas o DTO, mantendo a senha e o CPF protegidos
        return ResponseEntity.status(HttpStatus.CREATED).body(DTOMapper.toUserDTO(userSalvo));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> inativarUser(@PathVariable Long id) {
        userService.inativarUser(id);
        return ResponseEntity.noContent().build();
    }
}
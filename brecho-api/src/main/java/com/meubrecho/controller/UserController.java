package com.meubrecho.controller;

import com.meubrecho.model.User;
import com.meubrecho.service.UserService;
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
    public ResponseEntity<User> cadastrarUser(@RequestBody User novoUser) {
        User userSalvo = userService.cadastrarUser(novoUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(userSalvo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> inativarUser(@PathVariable Long id) {
        userService.inativarUser(id);
        return ResponseEntity.noContent().build();
    }
}
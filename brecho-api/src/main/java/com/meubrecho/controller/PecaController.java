package com.meubrecho.controller;

import com.meubrecho.model.Peca;
import com.meubrecho.model.enums.Categoria;
import com.meubrecho.service.PecaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// @RestController diz: "Sou o Garçom da web! Falo o idioma JSON."
@RestController
// @RequestMapping diz o endereço principal desse garçom. Ex: http://localhost:8080/pecas
@RequestMapping("/pecas")
public class PecaController {


    @Autowired
    private PecaService pecaService;

    // 1. CADASTRAR PEÇA (O site envia os dados, e nós salvamos)
    // @PostMapping significa que estamos recebendo dados novos (Método POST)
    @PostMapping
    public ResponseEntity<Peca> cadastrarPeca(@RequestBody Peca novaPeca) {
        // @RequestBody avisa: "Pegue o JSON que veio no corpo da requisição e transforme no objeto Peca!"
        Peca pecaSalva = pecaService.cadastrarPeca(novaPeca);
        // Retornamos 201 (CREATED) que é o código HTTP de sucesso para "Criado com sucesso!"
        return ResponseEntity.status(HttpStatus.CREATED).body(pecaSalva);
    }

    // 2. VER A VITRINE (Qualquer pessoa acessando o site)
    // @GetMapping significa que estamos apenas buscando/lendo dados (Método GET)
    @GetMapping("/vitrine")
    public ResponseEntity<List<Peca>> verVitrine() {
        List<Peca> vitrine = pecaService.buscarVitrine();
        return ResponseEntity.ok(vitrine); // Retorna 200 (OK) com a lista
    }

    // 3. MENU COM FILTRO DE CATEGORIAS (Ex: http://localhost:8080/pecas/menu?categoria=CALCADOS&disponiveis=true)
    @GetMapping("/menu")
    public ResponseEntity<List<Peca>> buscarPorCategoria(
            @RequestParam Categoria categoria,
            @RequestParam boolean disponiveis) {

        List<Peca> pecas = pecaService.buscarPorCategoria(categoria, disponiveis);
        return ResponseEntity.ok(pecas);
    }

    // 4. VER DETALHES DE UMA PEÇA ESPECÍFICA (Ex: http://localhost:8080/pecas/1)
    @GetMapping("/{id}")
    public ResponseEntity<Peca> verDetalhesPeca(@PathVariable Long id) {
        // @PathVariable pega aquele numero '1' lá da URL e joga pra dentro dessa variável Long id
        Peca peca = pecaService.buscarPorId(id);
        return ResponseEntity.ok(peca);
    }

    // 5. ATUALIZAR UMA PEÇA (Método PUT)
    @PutMapping("/{id}")
    public ResponseEntity<Peca> atualizarPeca(@PathVariable Long id, @RequestBody Peca pecaAtualizada) {
        Peca peca = pecaService.atualizarPeca(id, pecaAtualizada);
        return ResponseEntity.ok(peca);
    }

    // 6. DELETAR UMA PEÇA (Método DELETE)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPeca(@PathVariable Long id) {
        pecaService.deletarPeca(id);
        // Como deletamos, não temos corpo para retornar. Usamos o 204 (NO CONTENT).
        return ResponseEntity.noContent().build();
    }
}
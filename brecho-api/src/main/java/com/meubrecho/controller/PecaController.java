package com.meubrecho.controller;

import com.meubrecho.DTOMapper;
import com.meubrecho.model.dto.request.PecaRequestDTO;
import com.meubrecho.model.dto.response.PecaResponseDTO;
import com.meubrecho.model.Peca;
import com.meubrecho.service.PecaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/pecas")
public class PecaController {

    @Autowired
    private PecaService pecaService;

    @PostMapping
    public ResponseEntity<PecaResponseDTO> cadastrarPeca(@Valid @RequestBody PecaRequestDTO novaPeca) {
        Peca pecaSalva = pecaService.cadastrarPeca(novaPeca);
        return ResponseEntity.status(HttpStatus.CREATED).body(DTOMapper.toPecaDTO(pecaSalva));
    }

    @GetMapping("/vitrine")
    public ResponseEntity<List<PecaResponseDTO>> verVitrine() {
        List<Peca> vitrine = pecaService.buscarVitrine();

        List<PecaResponseDTO> vitrineDTO = vitrine.stream()
                .map(DTOMapper::toPecaDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(vitrineDTO);
    }

    @GetMapping("/menu")
    public ResponseEntity<List<PecaResponseDTO>> buscarPorCategoria(
            @RequestParam Long categoriaId, // Recebemos apenas o ID da categoria pela URL!
            @RequestParam boolean disponiveis) {

        // Passamos o ID para o Service
        List<Peca> pecas = pecaService.buscarPorCategoria(categoriaId, disponiveis);

        List<PecaResponseDTO> pecasDTO = pecas.stream()
                .map(DTOMapper::toPecaDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(pecasDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PecaResponseDTO> verDetalhesPeca(@PathVariable Long id) {
        Peca peca = pecaService.buscarPorId(id);
        return ResponseEntity.ok(DTOMapper.toPecaDTO(peca));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PecaResponseDTO> atualizarPeca(@PathVariable Long id, @Valid @RequestBody PecaRequestDTO pecaAtualizada) {
        Peca peca = pecaService.atualizarPeca(id, pecaAtualizada);
        return ResponseEntity.ok(DTOMapper.toPecaDTO(peca));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPeca(@PathVariable Long id) {
        pecaService.deletarPeca(id);
        return ResponseEntity.noContent().build();
    }
}
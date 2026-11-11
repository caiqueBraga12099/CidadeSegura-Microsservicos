package com.github.caiqueBraga12099.cidadesegura.atendimentos.controller;

import java.net.URI;
import java.util.List;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.github.caiqueBraga12099.cidadesegura.atendimentos.service.AtendimentoService;
import com.github.caiqueBraga12099.cidadesegura.atendimentos.dto.request.*;
import com.github.caiqueBraga12099.cidadesegura.atendimentos.dto.response.AtendimentoResponseDTO;

@RestController
@RequestMapping("/api/v1/atendimentos")
@Tag(name = "Atendimento")
public class AtendimentoController {

    private final AtendimentoService service;

    public AtendimentoController(AtendimentoService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Listar atendimentos")
    public ResponseEntity<List<AtendimentoResponseDTO>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar por identificador")
    public ResponseEntity<AtendimentoResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    @Operation(summary = "Criar atendimento")
    public ResponseEntity<AtendimentoResponseDTO> create(@Valid @RequestBody AtendimentoRequestDTO request) {
        AtendimentoResponseDTO response = service.create(request);
        URI uri = URI.create("/api/v1/atendimentos/" + response.getId());
        return ResponseEntity.created(uri).body(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar dados cadastrais")
    public ResponseEntity<AtendimentoResponseDTO> update(@PathVariable Long id,
            @Valid @RequestBody AtendimentoRequestDTO request) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir antes do inicio do atendimento")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Alterar status via comunicacao sincrona")
    public ResponseEntity<AtendimentoResponseDTO> alterarStatus(@PathVariable Long id,
            @Valid @RequestBody StatusRequestDTO request) {
        return ResponseEntity.ok(service.alterarStatus(id, request));
    }
}

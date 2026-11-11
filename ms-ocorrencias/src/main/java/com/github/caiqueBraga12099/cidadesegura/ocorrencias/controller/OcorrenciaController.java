package com.github.caiqueBraga12099.cidadesegura.ocorrencias.controller;

import java.net.URI;
import java.util.List;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.github.caiqueBraga12099.cidadesegura.ocorrencias.service.OcorrenciaService;
import com.github.caiqueBraga12099.cidadesegura.ocorrencias.dto.request.*;
import com.github.caiqueBraga12099.cidadesegura.ocorrencias.dto.response.OcorrenciaResponseDTO;

@RestController
@RequestMapping("/api/v1/ocorrencias")
@Tag(name = "Ocorrencia")
public class OcorrenciaController {

    private final OcorrenciaService service;

    public OcorrenciaController(OcorrenciaService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Listar ocorrencias")
    public ResponseEntity<List<OcorrenciaResponseDTO>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar por identificador")
    public ResponseEntity<OcorrenciaResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    @Operation(summary = "Criar ocorrencia")
    public ResponseEntity<OcorrenciaResponseDTO> create(@Valid @RequestBody OcorrenciaRequestDTO request) {
        OcorrenciaResponseDTO response = service.create(request);
        URI uri = URI.create("/api/v1/ocorrencias/" + response.getId());
        return ResponseEntity.created(uri).body(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar dados cadastrais")
    public ResponseEntity<OcorrenciaResponseDTO> update(@PathVariable Long id,
            @Valid @RequestBody OcorrenciaRequestDTO request) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir antes do inicio do atendimento")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Alterar status (contrato chamado pelo atendimento)")
    public ResponseEntity<OcorrenciaResponseDTO> alterarStatus(@PathVariable Long id,
            @Valid @RequestBody StatusRequestDTO request) {
        return ResponseEntity.ok(service.alterarStatus(id, request));
    }
}

package com.github.caiqueBraga12099.cidadesegura.atendimentos.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import com.github.caiqueBraga12099.cidadesegura.atendimentos.client.dto.*;

@FeignClient(name = "ms-ocorrencias", fallbackFactory = OcorrenciaFallbackFactory.class)
public interface OcorrenciaClient {

    @GetMapping("/api/v1/ocorrencias/{id}")
    OcorrenciaResponseDTO buscar(@PathVariable("id") Long id);

    @PatchMapping("/api/v1/ocorrencias/{id}/status")
    OcorrenciaResponseDTO alterarStatus(@PathVariable("id") Long id,
            @RequestBody OcorrenciaStatusRequestDTO request);
}

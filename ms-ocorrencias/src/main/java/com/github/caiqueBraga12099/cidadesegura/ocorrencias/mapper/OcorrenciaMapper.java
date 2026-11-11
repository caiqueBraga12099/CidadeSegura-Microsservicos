package com.github.caiqueBraga12099.cidadesegura.ocorrencias.mapper;

import com.github.caiqueBraga12099.cidadesegura.ocorrencias.entity.Ocorrencia;
import com.github.caiqueBraga12099.cidadesegura.ocorrencias.dto.request.OcorrenciaRequestDTO;
import com.github.caiqueBraga12099.cidadesegura.ocorrencias.dto.response.OcorrenciaResponseDTO;

public final class OcorrenciaMapper {

    private OcorrenciaMapper() {
    }

    public static OcorrenciaResponseDTO toResponse(Ocorrencia entity) {
        return new OcorrenciaResponseDTO(entity.getId(), entity.getTitulo(), entity.getDescricao(), entity.getEndereco(), entity.getBairro(), entity.getStatus(), entity.getDataCriacao());
    }

    public static void atualizar(Ocorrencia entity, OcorrenciaRequestDTO request) {
        entity.setTitulo(request.getTitulo());
        entity.setDescricao(request.getDescricao());
        entity.setEndereco(request.getEndereco());
        entity.setBairro(request.getBairro());
    }
}

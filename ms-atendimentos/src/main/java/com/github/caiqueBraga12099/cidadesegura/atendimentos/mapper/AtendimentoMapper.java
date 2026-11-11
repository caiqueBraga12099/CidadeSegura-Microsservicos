package com.github.caiqueBraga12099.cidadesegura.atendimentos.mapper;

import com.github.caiqueBraga12099.cidadesegura.atendimentos.entity.Atendimento;
import com.github.caiqueBraga12099.cidadesegura.atendimentos.dto.request.AtendimentoRequestDTO;
import com.github.caiqueBraga12099.cidadesegura.atendimentos.dto.response.AtendimentoResponseDTO;

public final class AtendimentoMapper {

    private AtendimentoMapper() {
    }

    public static AtendimentoResponseDTO toResponse(Atendimento entity) {
        return new AtendimentoResponseDTO(entity.getId(), entity.getOcorrenciaId(), entity.getEquipe(), entity.getResponsavel(), entity.getObservacao(), entity.getStatus(), entity.getDataCriacao());
    }

    public static void atualizar(Atendimento entity, AtendimentoRequestDTO request) {
        entity.setEquipe(request.getEquipe());
        entity.setResponsavel(request.getResponsavel());
        entity.setObservacao(request.getObservacao());
    }
}

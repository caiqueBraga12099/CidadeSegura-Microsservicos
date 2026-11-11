package com.github.caiqueBraga12099.cidadesegura.atendimentos.dto.response;

import com.github.caiqueBraga12099.cidadesegura.atendimentos.entity.StatusAtendimento;
import java.time.LocalDateTime;


public class AtendimentoResponseDTO {

    private Long id;

    private Long ocorrenciaId;

    private String equipe;

    private String responsavel;

    private String observacao;

    private StatusAtendimento status;

    private LocalDateTime dataCriacao;

    public AtendimentoResponseDTO() {
    }

    public AtendimentoResponseDTO(Long id, Long ocorrenciaId, String equipe, String responsavel, String observacao, StatusAtendimento status, LocalDateTime dataCriacao) {
        this.id = id;
        this.ocorrenciaId = ocorrenciaId;
        this.equipe = equipe;
        this.responsavel = responsavel;
        this.observacao = observacao;
        this.status = status;
        this.dataCriacao = dataCriacao;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOcorrenciaId() {
        return ocorrenciaId;
    }

    public void setOcorrenciaId(Long ocorrenciaId) {
        this.ocorrenciaId = ocorrenciaId;
    }

    public String getEquipe() {
        return equipe;
    }

    public void setEquipe(String equipe) {
        this.equipe = equipe;
    }

    public String getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(String responsavel) {
        this.responsavel = responsavel;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public StatusAtendimento getStatus() {
        return status;
    }

    public void setStatus(StatusAtendimento status) {
        this.status = status;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }
}

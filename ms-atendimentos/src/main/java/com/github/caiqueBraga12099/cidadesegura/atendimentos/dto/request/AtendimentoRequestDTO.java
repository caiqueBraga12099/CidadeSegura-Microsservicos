package com.github.caiqueBraga12099.cidadesegura.atendimentos.dto.request;

import jakarta.validation.constraints.*;


public class AtendimentoRequestDTO {

    @NotNull(message = "Informe a ocorrencia")
    @Positive(message = "O identificador deve ser positivo")
    private Long ocorrenciaId;

    @NotBlank(message = "Campo obrigatorio")
    @Size(min = 3, max = 100, message = "Use entre 3 e 100 caracteres")
    private String equipe;

    @NotBlank(message = "Campo obrigatorio")
    @Size(min = 3, max = 100, message = "Use entre 3 e 100 caracteres")
    private String responsavel;

    @NotBlank(message = "Campo obrigatorio")
    @Size(min = 3, max = 1000, message = "Use entre 3 e 1000 caracteres")
    private String observacao;

    public AtendimentoRequestDTO() {
    }

    public AtendimentoRequestDTO(Long ocorrenciaId, String equipe, String responsavel, String observacao) {
        this.ocorrenciaId = ocorrenciaId;
        this.equipe = equipe;
        this.responsavel = responsavel;
        this.observacao = observacao;
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
}

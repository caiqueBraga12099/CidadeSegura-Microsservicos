package com.github.caiqueBraga12099.cidadesegura.atendimentos.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "atendimentos")
public class Atendimento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private Long ocorrenciaId;

    @Column(nullable = false, length = 100)
    private String equipe;

    @Column(nullable = false, length = 100)
    private String responsavel;

    @Column(nullable = false, length = 1000)
    private String observacao;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private StatusAtendimento status;

    @Column(nullable = false)
    private LocalDateTime dataCriacao;

    @Version
    @Column(nullable = false)
    private Long versao;

    @Column(nullable = false, unique = true, length = 36)
    private String referencia;

    public Atendimento() {
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

    public Long getVersao() {
        return versao;
    }

    public void setVersao(Long versao) {
        this.versao = versao;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }
}

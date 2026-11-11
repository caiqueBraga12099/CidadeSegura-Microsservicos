package com.github.caiqueBraga12099.cidadesegura.ocorrencias.dto.response;

import com.github.caiqueBraga12099.cidadesegura.ocorrencias.entity.StatusOcorrencia;
import java.time.LocalDateTime;


public class OcorrenciaResponseDTO {

    private Long id;

    private String titulo;

    private String descricao;

    private String endereco;

    private String bairro;

    private StatusOcorrencia status;

    private LocalDateTime dataCriacao;

    public OcorrenciaResponseDTO() {
    }

    public OcorrenciaResponseDTO(Long id, String titulo, String descricao, String endereco, String bairro, StatusOcorrencia status, LocalDateTime dataCriacao) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.endereco = endereco;
        this.bairro = bairro;
        this.status = status;
        this.dataCriacao = dataCriacao;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public StatusOcorrencia getStatus() {
        return status;
    }

    public void setStatus(StatusOcorrencia status) {
        this.status = status;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }
}

package com.github.caiqueBraga12099.cidadesegura.ocorrencias.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "ocorrencias")
public class Ocorrencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 120)
    private String titulo;

    @Column(nullable = false, length = 1000)
    private String descricao;

    @Column(nullable = false, length = 200)
    private String endereco;

    @Column(nullable = false, length = 100)
    private String bairro;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private StatusOcorrencia status;

    @Column(nullable = false)
    private LocalDateTime dataCriacao;

    @Version
    @Column(nullable = false)
    private Long versao;

    @Column(length = 36)
    private String referenciaAtendimento;

    public Ocorrencia() {
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

    public Long getVersao() {
        return versao;
    }

    public void setVersao(Long versao) {
        this.versao = versao;
    }

    public String getReferenciaAtendimento() {
        return referenciaAtendimento;
    }

    public void setReferenciaAtendimento(String referenciaAtendimento) {
        this.referenciaAtendimento = referenciaAtendimento;
    }
}
